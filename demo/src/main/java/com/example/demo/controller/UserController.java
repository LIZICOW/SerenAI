package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.domain.User;
import com.example.demo.utils.Result;
import org.apache.ibatis.jdbc.Null;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<User> loginController(@RequestBody User LoginUser){
        User user = userService.loginService(LoginUser.getUname(), LoginUser.getPassword());
        if(user!=null){
            return Result.success(user,"登录成功！");
        }else{
            return Result.error("123","账号或密码错误！");
        }
    }

    @PostMapping("/register")
    public Result<User> registController(@RequestBody User newUser){
        // 获取当前本地时间
        newUser.setRegistrationTime(LocalDateTime.now());
        newUser.setSignature("");
        User user = userService.registerService(newUser);
        if(user != null){
            return Result.success(user, "注册成功！");
        } else {
            return Result.error("456", "用户名已存在！");
        }
    }

    @GetMapping("/getBasicInfo/{uid}")
    public Result<Map<String, Object>> getBasicInfoController(@PathVariable long uid) {
        // 调用 UserService 方法，获取用户基本信息
        User user = userService.getBasicInfoService(uid);

        if (user != null) {
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime registrationTime = user.getRegistrationTime();

            Duration duration = Duration.between(registrationTime, currentTime);
            long hoursDifference = duration.toHours(); // 计算小时差

            // 构建返回结果
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("user", user);
            resultMap.put("hoursDifference", hoursDifference);

            return Result.success(resultMap, "获取用户基本信息成功！");
        } else {
            return Result.error("789", "用户不存在！");
        }
    }

    @PostMapping("/updateProfile")
    public Result<User> updateProfileController(@RequestBody User user) {
        long uid = user.getUid();
        String newName = user.getUname();
        String newSignature = user.getSignature();

        // 调用 UserService 方法，更新用户名和个人签名
        boolean updated = userService.updateProfileService(uid, newName, newSignature);

        if (updated) {
            return Result.success(null, "用户信息更新成功！");
        } else {
            return Result.error("456", "用户不存在！");
        }
    }

}
