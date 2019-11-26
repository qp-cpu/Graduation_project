package life.majiang.community.service;


import life.majiang.community.dao.UserEntityMapper;
import life.majiang.community.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserEntityMapper userDao;

    public UserEntity getuser(int id){
     return  userDao.getuser(id);
    }
    public Integer add(UserEntity userEntity)
    {
        return userDao.add(userEntity);
    }
    public UserEntity selectBytoken(String token){
        return userDao.selectBytoken(token);
    }
    public void createOrUpdate(UserEntity userEntity)
    {
        UserEntity dbuserEntity=userDao.findByAccountId(userEntity.getAccountId());
        if(dbuserEntity==null)
        {
            //插入
            userEntity.setGmtCreate(System.currentTimeMillis());
            userEntity.setGmtModified(userEntity.getGmtCreate());
            userDao.add(userEntity);
        }
        else {
            //更新
            dbuserEntity.setGmtModified(System.currentTimeMillis());
            dbuserEntity.setAvatarUrl(userEntity.getAvatarUrl());
            dbuserEntity.setName(userEntity.getName());
            dbuserEntity.setToken(userEntity.getToken());
            userDao.updatetoken(dbuserEntity);
        }
    }

    public UserEntity selectBYid(Integer id) {
      return  userDao.selectByPrimaryKey(id);
    }
}
