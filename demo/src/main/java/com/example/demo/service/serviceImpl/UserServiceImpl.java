package com.example.demo.service.serviceImpl;

import com.example.demo.domain.User;
import com.example.demo.repository.UserDao;
import com.example.demo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserDao userDao;

    @Override
    public User loginService(String uname, String password) {
        User user = userDao.findByUnameAndPassword(uname, password);

        if(user != null){
            user.setPassword("");
        }

        return user;
    }

    @Override
    public User registerService(User user) {
        if(userDao.findByUname(user.getUname()) != null){
            return null;
        }

        User newUser = userDao.save(user);
        newUser.setPassword("");
        return newUser;
    }

    @Override
    public User getBasicInfoService(long uid) {
        // 调用数据库查询方法，根据 uid 获取用户基本信息
        // 返回一个 User 对象，包含用户的基本信息
        return userDao.findByUid(uid);
    }

    public boolean updateProfileService(long uid, String newName, String newSignature) {
        User user = userDao.getOne(uid);

        if (user != null) {
            user.setUname(newName);
            user.setSignature(newSignature);
            userDao.save(user);
            return true;
        }

        return false;
    }

}
