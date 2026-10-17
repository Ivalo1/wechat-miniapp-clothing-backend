package com.ivalo1.wechatbackend.service;

import com.ivalo1.wechatbackend.mapper.UserMapper;
import com.ivalo1.wechatbackend.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User findById(Long id) {
        return userMapper.findById(id);
    }

    public User findOrCreateByOpenId(String openId) {
        User u = userMapper.findByOpenId(openId);
        if (u != null) return u;
        User nu = new User();
        nu.setOpenId(openId);
        nu.setNickname("微信用户");
        nu.setAvatarUrl("");
        nu.setCreatedAt(LocalDateTime.now());
        userMapper.insert(nu);
        return nu;
    }
}
