package life.majiang.community.controller;

import life.majiang.community.dto.ComentCreateDto;
import life.majiang.community.dto.CommentDto;
import life.majiang.community.dto.ResultDto;
import life.majiang.community.entity.CommentEntity;
import life.majiang.community.entity.UserEntity;
import life.majiang.community.exception.CustmizeException;
import life.majiang.community.exception.CustomizeErrorcode;
import life.majiang.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    private static Integer TYPE=2;

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public Object post(@RequestBody ComentCreateDto commentDto,
                       HttpServletRequest request){

        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if (user==null)
        {
            return new CustmizeException(CustomizeErrorcode.NO_LOGIN);
        }
        if (commentDto ==null || StringUtils.isBlank(commentDto.getContent()))
        {
            return new CustmizeException(CustomizeErrorcode.COMMENT_IS_EMPTY);
        }
        CommentEntity record = new CommentEntity();
        record.setParentId(commentDto.getParentId());
        record.setContent(commentDto.getContent());
        record.setType(commentDto.getType());
        record.setGmtCreate(System.currentTimeMillis());
        record.setGmtModified(System.currentTimeMillis());
        record.setLikeCount(0L);
        record.setCommentCount(0);
        record.setCommentor(user.getId());
        commentService.insert(record);
        return ResultDto.okOf();
    }

    @GetMapping("/comment/{id}")
    @ResponseBody
    public  ResultDto<List<CommentDto>> comments(@PathVariable("id") Integer id,
                               Model model){

        List<CommentDto> commentDtos = commentService.ListByQuestionId(id,TYPE);
        return ResultDto.okOf(commentDtos);
    }
}
