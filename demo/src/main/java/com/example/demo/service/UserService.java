package com.example.demo.service;

import com.example.demo.domain.User;

public interface UserService {
    User loginService(String uname, String password);

    User registerService(User user);

    User getBasicInfoService(long uid);

    boolean updateProfileService(long uid, String newName, String newSignature);
}
