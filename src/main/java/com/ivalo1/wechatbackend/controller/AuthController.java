package com.ivalo1.wechatbackend.controller;

import com.ivalo1.wechatbackend.common.ApiResponse;
import com.ivalo1.wechatbackend.dto.LoginRequest;
import com.ivalo1.wechatbackend.model.User;
import com.ivalo1.wechatbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ApiResponse<User> login(@RequestBody LoginRequest req) {
        // Placeholder: in production, exchange `code` with WeChat to get openId
        // For now we mock or create user by openId = req.getCode()
        if (req.getCode() == null || req.getCode().isEmpty()) {
            return ApiResponse.error("missing code");
        }
        User user = userService.findOrCreateByOpenId(req.getCode());
        return ApiResponse.ok(user);
    }
}
