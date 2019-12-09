package life.majiang.community.service;

import life.majiang.community.dao.CommentEntityMapper;
import life.majiang.community.dao.NtificationEntityMapper;
import life.majiang.community.dao.UserEntityMapper;
import life.majiang.community.dto.NotificationDto;
import life.majiang.community.dto.PageDto;
import life.majiang.community.dto.PageNtifcationdto;
import life.majiang.community.entity.CommentEntity;
import life.majiang.community.entity.NtificationEntity;
import life.majiang.community.entity.NtificationEntityExample;
import life.majiang.community.entity.UserEntity;
import life.majiang.community.enums.NtificationStatusEnums;
import life.majiang.community.enums.NtificationTypeEnums;
import life.majiang.community.exception.CustmizeException;
import life.majiang.community.exception.CustomizeErrorcode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NtificationEntityService {

    @Autowired
    private NtificationEntityMapper ntidao;
    @Autowired
    private UserEntityMapper userdao;
    @Autowired
    private CommentEntityMapper commentEntityMapper;

    public PageNtifcationdto list(Long id, Integer page, Integer size) {
        PageNtifcationdto pageDto = new PageNtifcationdto();
        Integer totalcount=ntidao.count();
        pageDto.setPagenation(totalcount,page,size);

        //对违规值进行处理
        if(page<1)
        {
            page=1;
        }
        if(page>pageDto.getTotalpage())
        {
            page=pageDto.getTotalpage();
        }

        Integer ofsize= size * (page-1);
        List<NtificationEntity> ntifacations=ntidao.selectAll(id,ofsize,size);
        List<NotificationDto> notificationDtos=new ArrayList<>();
        if(ntifacations.size()==0)
        {
            return pageDto;
        }
        for (NtificationEntity ntifacation : ntifacations) {
            NotificationDto dto = new NotificationDto();
            BeanUtils.copyProperties(ntifacation,dto);
            dto.setTypeName(NtificationTypeEnums.nameOfType(ntifacation.getType()));
            notificationDtos.add(dto);
        }
        pageDto.setNotificationDtos(notificationDtos);

        return  pageDto;
    }

    public Integer unreadCount(Long userid) {
        NtificationEntityExample example=new NtificationEntityExample();
        example.createCriteria()
                .andStatusEqualTo(NtificationStatusEnums.UNREAD.getStatus())
                .andReciverEqualTo(userid);
        return  ntidao.countByExample(example);
    }

    public NotificationDto read(Long id, UserEntity userEntity) {
        NtificationEntity ntificationEntity = ntidao.selectByPrimaryKey(id.intValue());
        if (ntificationEntity==null)
        {
            throw  new  CustmizeException(CustomizeErrorcode.NOTIFCATION_NOT_FOUND);
        }
        if (ntificationEntity.getReciver().intValue()!=userEntity.getId())
        {
            throw  new  CustmizeException(CustomizeErrorcode.READ_NOTIFCATION_FAIL);
        }
        NtificationEntity entity=new NtificationEntity();
        entity.setId(id.intValue());
        entity.setStatus(NtificationStatusEnums.READ.getStatus());
        ntidao.updateByPrimaryKeySelective(entity);
        NotificationDto dto = new NotificationDto();
        BeanUtils.copyProperties(ntificationEntity,dto);
        dto.setTypeName(NtificationTypeEnums.nameOfType(ntificationEntity.getType()));
        return  dto;
    }

    public CommentEntity selectCommentParentid(Long outerid) {
        CommentEntity commentEntity = commentEntityMapper.selectByPrimaryKey(outerid);
        return commentEntity;
    }
}
