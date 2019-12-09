package life.majiang.community.controller;

import life.majiang.community.dto.NotificationDto;
import life.majiang.community.entity.CommentEntity;
import life.majiang.community.entity.UserEntity;
import life.majiang.community.enums.ContentTypeEnums;
import life.majiang.community.enums.NtificationTypeEnums;
import life.majiang.community.service.NtificationEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NtificationController {
    @Autowired
    private NtificationEntityService ntiService;

    @GetMapping("notification/{id}")
    public String notification(@PathVariable("id") Long id,
                          HttpServletRequest request) {
        UserEntity userEntity=(UserEntity) request.getSession().getAttribute("user");
        if(userEntity==null)
        {
            return "redirect:/";
        }
        NotificationDto notificationDto= ntiService.read(id,userEntity);
        if(NtificationTypeEnums.REPY_COMMENT.getStatus() == notificationDto.getType()||
                NtificationTypeEnums.REPY_QUESTION.getStatus()==notificationDto.getType())
        {
            if(notificationDto.getType() == ContentTypeEnums.QUESTION.getType())
            {
                return "redirect:/question/"+notificationDto.getOuterid();

            }
            else {
                CommentEntity commentEntity = ntiService.selectCommentParentid(notificationDto.getOuterid());
                return "redirect:/question/"+commentEntity.getParentId();
            }
        }
        return "profile";
    }

}
