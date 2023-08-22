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
}
