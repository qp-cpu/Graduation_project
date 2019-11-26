package life.majiang.community.dao;

import life.majiang.community.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);
    public UserEntity getuser(int id);
    public Integer add(UserEntity userEntity);
    public UserEntity selectBytoken(@Param("token") String token);

    public UserEntity findByAccountId(@Param("accountId") String accountId);

    public  void updatetoken(UserEntity dbuserEntity);
}