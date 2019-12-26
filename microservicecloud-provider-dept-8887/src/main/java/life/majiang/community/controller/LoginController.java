package life.majiang.community.controller;

import life.majiang.community.entity.UserEntity;
import life.majiang.community.exception.CustmizeException;
import life.majiang.community.exception.CustomizeErrorcode;
import life.majiang.community.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LoginController {

    @Value("${ip.addr}")
    private String ipaddr;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("ipaddr",ipaddr);
        return "login";
    }

    @PostMapping("/login")
    public String dologin(@RequestParam(value = "username",required = false) String username,
                          @RequestParam(value = "password",required =false) String password,
                          HttpServletResponse response){
        UserEntity userEntity=new UserEntity();
        userEntity.setName(username);
        userEntity.setAccountId(password);
        UserEntity selectuser = userService.selectuser(userEntity);
        if (selectuser !=null)
        {
            Cookie token = new Cookie("token", selectuser.getToken());
            response.addCookie(token);
            return "redirect:/";
        }
        else {
            throw new CustmizeException(CustomizeErrorcode.SIGN_FALE);
        }
    }

    @PostMapping("/sign")
    public String dosign(@RequestParam(value = "username",required = false) String username,
                         @RequestParam(value = "password",required =false) String password,
                         @RequestParam(value = "description" ,required = false) String description,
                         @RequestParam(value = "avatarurl",required = false) String avatarurl
    ) throws Exception {
        UserEntity userEntit=new UserEntity();
        userEntit.setName(username);
        userEntit.setAccountId(password);
        userEntit.setGmtCreate(System.currentTimeMillis());
        userEntit.setGmtModified(System.currentTimeMillis());
        userEntit.setBio(description);
        userEntit.setToken(UUID.randomUUID().toString());
        String filename=UUID.randomUUID().toString()+".png";
        String avatar_url = "http://121.41.85.42/"+userService.getphoto(avatarurl);
        userEntit.setAvatarUrl(avatar_url);
        if(StringUtils.isBlank(username))
        {
            throw new CustmizeException(CustomizeErrorcode.SIGN_IS_NULL);
        }
        userService.insert(userEntit);
        return "login";
    }

    @GetMapping("/sign")
    public String sign(){
        return "sign";
    }
}
