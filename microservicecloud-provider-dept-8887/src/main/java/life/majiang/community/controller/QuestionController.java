package life.majiang.community.controller;

import life.majiang.community.dto.ComentCreateDto;
import life.majiang.community.dto.CommentDto;
import life.majiang.community.dto.PublishDto;
import life.majiang.community.service.CommentService;
import life.majiang.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private PublishService publishService;
    @Autowired
    private CommentService commentService;
    private static Integer TYPE=1;
    @GetMapping("/question/{creator}")
    public  String question(@PathVariable("creator") Integer id,
                            Model model){
        PublishDto publishdto = publishService.getBYid(id);
        List<PublishDto>  releatedPublish=publishService.selectRelated(publishdto);
        List<CommentDto> comentDtoList = commentService.ListByQuestionId(id,TYPE);
        //增加阅读数
        publishService.intView(id);
        model.addAttribute("question",publishdto);
        model.addAttribute("comentDtoList",comentDtoList);
        model.addAttribute("releatedPublish",releatedPublish);
        return "question";
    }
}
