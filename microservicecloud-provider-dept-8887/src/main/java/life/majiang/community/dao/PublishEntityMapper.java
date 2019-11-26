package life.majiang.community.dao;

import life.majiang.community.entity.PublishEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PublishEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PublishEntity record);

    int insertSelective(PublishEntity record);

    PublishEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PublishEntity record);

    int updateByPrimaryKey(PublishEntity record);

    public Integer insertpublish(PublishEntity publishEntity);

    public List<PublishEntity> selectAll(@Param("ofszie") Integer ofszie, @Param("size") Integer size);
    public  Integer count();
    public  Integer count1(@Param("creator") Integer creator);
    List<PublishEntity> list(@Param("id") Integer id, @Param("ofszie") Integer ofszie, @Param("size") Integer size);
    public  PublishEntity getBYid(@Param("id") Integer id);

    public  void updateByid(PublishEntity publishEntity);

    void updateViewCountBYid(@Param("id") Integer id);
    int incCommentCount(PublishEntity record);

    List<PublishEntity> selectRelated(PublishEntity publishEntity);
}