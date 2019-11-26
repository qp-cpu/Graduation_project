package life.majiang.community.controller;


import life.majiang.community.dto.PageDto;
import life.majiang.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
}
