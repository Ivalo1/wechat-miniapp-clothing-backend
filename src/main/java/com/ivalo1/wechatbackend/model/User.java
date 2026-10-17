package com.ivalo1.wechatbackend.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String openId; // 微信 openId
    private String nickname;
    private String avatarUrl;
    private String phone;
    private LocalDateTime createdAt;
}
