package com.ivalo1.wechatbackend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String code; // WeChat login code or username/password placeholder
}
