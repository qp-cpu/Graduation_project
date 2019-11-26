package life.majiang.community.controller;

import life.majiang.community.dto.AccesstokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.entity.UserEntity;
import life.majiang.community.provider.GithubProvider;
import life.majiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private UserService userService;



    @Value("${github.client.id}")
    private String client_Id;

    @Value("${github.client.secrect}")
    private String client_secrect;

    @Value("${github.redirect.url}")
    private String redirect_url;

    @Autowired
   private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam("state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setCode(code);
        accesstokenDTO.setState(state);
        accesstokenDTO.setRedirect_uri(redirect_url);
        accesstokenDTO.setClient_id(client_Id);
        accesstokenDTO.setClient_secret(client_secrect);
        String accessToken = githubProvider.getAccessToken(accesstokenDTO);
        GithubUser githubUser = githubProvider.getuser(accessToken);
        if(githubUser!=null)
       {
           String accountId=String.valueOf(githubUser.getId());
           //登录成功
           UserEntity userEntity = new UserEntity();
           String token = UUID.randomUUID().toString();
           userEntity.setToken(token);
           userEntity.setName(githubUser.getName());
           userEntity.setAccountId(accountId);
           userEntity.setAvatarUrl(String.valueOf(githubUser.getId()));
           userEntity.setBio(githubUser.getBio());
           userEntity.setAvatarUrl(githubUser.getAvatar_url());
           //把获取的信息注入数据库
           userService.createOrUpdate(userEntity);
           System.out.println(token);
           response.addCookie(new Cookie("token",token));

           return "redirect:/";
       }
       else
       {
           //登录失败，重新登录
           return "redirect:/";
       }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return  "redirect:/";
    }
}