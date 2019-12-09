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

    UserEntity getuser(int id);

    Integer add(UserEntity userEntity);

    UserEntity selectBytoken(@Param("token") String token);

    UserEntity findByAccountId(@Param("accountId") String accountId);

    void updatetoken(UserEntity dbuserEntity);

    UserEntity selectBYnameAndpwd(UserEntity userEntity);
}