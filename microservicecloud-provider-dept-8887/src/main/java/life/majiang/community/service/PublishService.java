package life.majiang.community.service;


import life.majiang.community.dao.PublishEntityMapper;
import life.majiang.community.dao.UserEntityMapper;
import life.majiang.community.dto.PageDto;
import life.majiang.community.dto.PublishDto;
import life.majiang.community.entity.PublishEntity;
import life.majiang.community.entity.UserEntity;
import life.majiang.community.exception.CustmizeException;
import life.majiang.community.exception.CustomizeErrorcode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublishService {
    @Autowired
    PublishEntityMapper publishDao;
    @Autowired
    UserEntityMapper userDao;

    public PageDto selectAll(Integer page, Integer size){
        PageDto pageDto = new PageDto();
        Integer totalcount=publishDao.count();
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
        List<PublishEntity> publishEntityList=publishDao.selectAll(ofsize,size);
        List<PublishDto> publishDtos=new ArrayList<>();

        for (PublishEntity publishEntity:publishEntityList)
        {
            UserEntity userEntity = userDao.getuser(publishEntity.getCreator());
            if(userEntity!=null) {
                PublishDto publishDto = new PublishDto();
                BeanUtils.copyProperties(publishEntity, publishDto);
                publishDto.setUserEntity(userEntity);
                publishDtos.add(publishDto);
            }
        }
        pageDto.setPublishDtos(publishDtos);
        return pageDto;
    }

    public PageDto list(Integer id, Integer page, Integer size) {
        PageDto pageDto = new PageDto();
        Integer totalcount=publishDao.count1(id);
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
        List<PublishEntity> publishEntityList=publishDao.list(id,ofsize,size);
        List<PublishDto> publishDtos=new ArrayList<>();

        for (PublishEntity publishEntity:publishEntityList)
        {
            UserEntity userEntity = userDao.getuser(publishEntity.getCreator());
            if(userEntity!=null) {
                PublishDto publishDto = new PublishDto();
                BeanUtils.copyProperties(publishEntity, publishDto);
                publishDto.setUserEntity(userEntity);
                publishDtos.add(publishDto);
            }
        }
        pageDto.setPublishDtos(publishDtos);

        return  pageDto;
    }

    public PublishDto getBYid(Integer id) {
        PublishEntity publishentity = publishDao.getBYid(id);
        if(publishentity==null)
        {
            throw new CustmizeException(CustomizeErrorcode.QUESTION_NOT);
        }
        PublishDto publishDto=new PublishDto();
        BeanUtils.copyProperties(publishentity,publishDto);
        UserEntity userEntity = userDao.getuser(publishentity.getCreator());
        publishDto.setUserEntity(userEntity);
        return publishDto;
    }

    public void inserOrUpdatetpublish(PublishEntity publishEntity) {
        //创建问题
        if (publishEntity.getId()==null)
        {
            publishEntity.setGmtCreate(System.currentTimeMillis());
            publishEntity.setGmtModified(System.currentTimeMillis());
            publishEntity.setViewCount(0);
            publishEntity.setLikeCount(0);
            publishEntity.setCommentCount(0);
            publishDao.insert(publishEntity);
        }
        else {
            //更新
           publishEntity.setGmtModified(System.currentTimeMillis());
           publishDao.updateByid(publishEntity);
        }
    }

    public void intView(Integer id) {
        PublishEntity publishEntity = publishDao.selectByPrimaryKey(id);
        publishDao.updateViewCountBYid(id);
    }

    public List<PublishDto> selectRelated(PublishDto publishdto) {
        if(StringUtils.isBlank(publishdto.getTag()))
        {
                 return new ArrayList<>();
        }

            String tag = StringUtils.replace(publishdto.getTag(), ",", "|");
            PublishEntity publishEntity=new PublishEntity();
            publishEntity.setId(publishdto.getId());
            publishEntity.setTag(tag);
            List<PublishEntity> publishEntityList = publishDao.selectRelated(publishEntity);
            List<PublishDto> publishdtos = publishEntityList.stream().map(q -> {
            PublishDto publishDto1=new PublishDto();
            BeanUtils.copyProperties(q,publishDto1);
            return publishDto1;
        }).collect(Collectors.toList());
        return publishdtos;
    }
}
