package com.ivalo1.wechatbackend.dto;

import com.ivalo1.wechatbackend.model.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private User user;
}
