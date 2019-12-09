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

    Integer insertpublish(PublishEntity publishEntity);

    List<PublishEntity> selectAll(@Param("ofszie") Integer ofszie, @Param("size") Integer size);

    Integer count();

    Integer count1(@Param("creator") Integer creator);

    List<PublishEntity> list(@Param("id") Integer id,@Param("ofszie") Integer ofszie, @Param("size") Integer size);

    PublishEntity getBYid(@Param("id") Integer id);

    void updateByid(PublishEntity publishEntity);

    void updateViewCountBYid(@Param("id") Integer id);

    int incCommentCount(PublishEntity record);

    List<PublishEntity> selectRelated(PublishEntity publishEntity);

    Integer serachcount(@Param("serach") String serach);

    List<PublishEntity> serachAll(@Param("serach") String serach,@Param("ofszie") Integer ofszie, @Param("size") Integer size);
}