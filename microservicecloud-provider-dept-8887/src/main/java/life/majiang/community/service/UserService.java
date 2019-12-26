package life.majiang.community.service;


import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import life.majiang.community.dao.UserEntityMapper;
import life.majiang.community.entity.UserEntity;
import life.majiang.community.exception.CustmizeException;
import life.majiang.community.exception.CustomizeErrorcode;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

import java.net.HttpURLConnection;
import java.net.URL;



@Service
public class UserService {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

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

    public void insert(UserEntity userEntit) {
        userDao.insertSelective(userEntit);
    }

    public UserEntity selectuser(UserEntity userEntity) {
        return userDao.selectBYnameAndpwd(userEntity);
    }

    public String getphoto(String avatar_url) throws Exception {
        // 构造URL
        URL url = new URL(avatar_url);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //通过输入流获取图片数据
        InputStream inputStream = conn.getInputStream();
        //注！图片的大小如果错误则会出现图片上传成功但查看图片时图片部分丢失的情况
        StorePath storePath= fastFileStorageClient.uploadFile(inputStream, IOUtils.toByteArray(url).length,"png",null);
        inputStream.close();
        return storePath.getFullPath();
    }
}
