package life.majiang.community.service;

import life.majiang.community.dao.CommentEntityMapper;
import life.majiang.community.dao.NtificationEntityMapper;
import life.majiang.community.dao.PublishEntityMapper;
import life.majiang.community.dto.CommentDto;
import life.majiang.community.entity.CommentEntity;
import life.majiang.community.entity.NtificationEntity;
import life.majiang.community.entity.PublishEntity;
import life.majiang.community.entity.UserEntity;
import life.majiang.community.enums.ContentTypeEnums;
import life.majiang.community.enums.NtificationStatusEnums;
import life.majiang.community.enums.NtificationTypeEnums;
import life.majiang.community.exception.CustmizeException;
import life.majiang.community.exception.CustomizeErrorcode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentEntityMapper commentEntityMapper;

    @Autowired
    private PublishEntityMapper publishEntityMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private NtificationEntityMapper ntificationEntityMapper;

    @Transactional
    public void insert(CommentEntity record) {
        if(record.getParentId()==null || record.getParentId()==0)
        {
            throw new CustmizeException(CustomizeErrorcode.TARGET_PARAM_NOT_FIND);
        }
       if(record.getType()==null|| !ContentTypeEnums.isExist(record.getType()))
       {
           throw new CustmizeException(CustomizeErrorcode.TYPE_PARAM_ERROE);
       }
       if(record.getType()== ContentTypeEnums.COMMENT.getType())
       {
           //回复评论
            CommentEntity commentEntity = commentEntityMapper.selectByPrimaryKey(record.getParentId());
            if(commentEntity==null)
            {
                throw new CustmizeException(CustomizeErrorcode.COMMENT_NOT_FIND);
            }
            commentEntityMapper.insert(record);

//            增加评论数
           commentEntityMapper.inccommentcount(record.getParentId().intValue());
//           创建评论通知通知
           createNotified(record, Long.valueOf(commentEntity.getCommentor()), NtificationTypeEnums.REPY_COMMENT.getStatus());
       }
       else {
           //回复问题
           PublishEntity publishEntity = publishEntityMapper.selectByPrimaryKey(record.getParentId().intValue());
           if(publishEntity==null)
           {
               throw new CustmizeException(CustomizeErrorcode.QUESTION_NOT);
           }
           commentEntityMapper.insert(record);
           publishEntity.setCommentCount(1);
           publishEntityMapper.incCommentCount(publishEntity);

//           回复问题通知
           createNotified(record, Long.valueOf(publishEntity.getCreator()), NtificationTypeEnums.REPY_COMMENT.getStatus());
       }
    }

//    创建回复
    private void createNotified(CommentEntity record, Long reciver, int type) {
        NtificationEntity ntificationEntity =new NtificationEntity();
        ntificationEntity.setGmtCreate(System.currentTimeMillis());
        ntificationEntity.setStatus(type);
        ntificationEntity.setOuterid(record.getParentId());
        ntificationEntity.setNotifier(Long.valueOf(record.getCommentor()));
        ntificationEntity.setStatus(NtificationStatusEnums.UNREAD.getStatus());
        ntificationEntity.setReciver(reciver);
        ntificationEntityMapper.insert(ntificationEntity);
    }

    public List<CommentDto> ListByQuestionId(Integer id, Integer type) {
        List<CommentEntity> commentEntities = commentEntityMapper.selectByParentid(id,type);
        if (commentEntities.size()==0)
        {
            return new ArrayList<>();
        }
        Set<Integer> commtoreres = commentEntities.stream().map(commentEntity -> commentEntity.getCommentor()).collect(Collectors.toSet());
        List<Integer> userids=new ArrayList<>();
        userids.addAll(commtoreres);
        ArrayList<UserEntity> userEntities=new ArrayList<>();
       for(Integer userId:userids)
       {
           UserEntity userEntity = userService.selectBYid(userId);
           userEntities.add(userEntity);
       }
     //获取评论人并转化为map
       Map<Integer, UserEntity> userEntityMap=userEntities.stream().collect(Collectors.toMap(userentity->userentity.getId(), userEntity -> userEntity));
     //转化comment为commentdto
       List<CommentDto> commentDtos=commentEntities.stream().map(commentEntity -> {
           CommentDto commentDto=new CommentDto();
           BeanUtils.copyProperties(commentEntity,commentDto);
           commentDto.setUserEntity(userEntityMap.get(commentEntity.getCommentor()));
           return  commentDto;
       }).collect(Collectors.toList());
        commentDtos.sort(Comparator.comparing(CommentDto::getGmtCreate).reversed());
        return commentDtos;
    }
}
