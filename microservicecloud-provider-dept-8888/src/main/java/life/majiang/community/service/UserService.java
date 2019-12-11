package life.majiang.community.service;


import life.majiang.community.dao.UserEntityMapper;
import life.majiang.community.entity.UserEntity;
import life.majiang.community.exception.CustmizeException;
import life.majiang.community.exception.CustomizeErrorcode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


@Service
public class UserService {

    @Value("${absoluteImgPath}")
    String absoluteImgPath;

    @Value("${sonImgPath}")
    String sonImgPath;

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


    public void getphoto(String avatar_url, String filename) {
        try{

            // 构造URL
            URL url = new URL(avatar_url);
            // 打开连接
            URLConnection con = url.openConnection();
            //设置请求超时为5s
            con.setConnectTimeout(5*1000);
            // 输入流
            InputStream is = con.getInputStream();

            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            File sf=new File(absoluteImgPath);
            if(!sf.exists()){
                sf.mkdirs();
            }
            OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
        }
        catch (Exception e)
        {
            throw  new CustmizeException(CustomizeErrorcode.READPHOTO_ERROR);
        }
    }
}
