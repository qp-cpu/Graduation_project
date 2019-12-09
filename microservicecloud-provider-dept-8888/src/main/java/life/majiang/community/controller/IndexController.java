package life.majiang.community.controller;


import life.majiang.community.dto.PageDto;
import life.majiang.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {

    @Autowired
    private PublishService publishService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size
    ) {
        PageDto pageDtoList = publishService.selectAll(page, size);
        model.addAttribute("publishs", pageDtoList);
        return "index";
    }


    @GetMapping("/serach")
    public  String serach(@RequestParam(name = "serach",required = false) String serach,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size,
                          Model model,
                          HttpServletRequest request){
        String  search1="%"+serach+"%";
        PageDto pageDtoList = publishService.serachAll(search1,page, size);
        model.addAttribute("publishs", pageDtoList);
        model.addAttribute("serach",serach);
        return "serach";
    }
}
