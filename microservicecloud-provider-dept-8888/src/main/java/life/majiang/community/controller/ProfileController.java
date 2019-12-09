package life.majiang.community.controller;

import life.majiang.community.dto.PageDto;
import life.majiang.community.dto.PageNtifcationdto;
import life.majiang.community.entity.UserEntity;
import life.majiang.community.service.NtificationEntityService;
import life.majiang.community.service.PublishService;
import life.majiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    PublishService publishService;
    @Autowired
    UserService userService;

    @Autowired
    private NtificationEntityService ntificationEntityService;

    @GetMapping("profile/{action}")
    public String profile(@PathVariable("action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size) {
        UserEntity userEntity=(UserEntity) request.getSession().getAttribute("user");
        if(userEntity==null)
        {
            return "redirect:/";
        }
        if (action.equals("question")) {
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的提问");
            PageDto pageDtoList= publishService.list(userEntity.getId(),page,size);
            model.addAttribute("publishs",pageDtoList);
        } else if (action.equals("replies")) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "我的回复");
            PageNtifcationdto pageDto=ntificationEntityService.list(Long.valueOf(userEntity.getId()),page,size);
            model.addAttribute("notification",pageDto);
        }

        return "profile";
    }


}

