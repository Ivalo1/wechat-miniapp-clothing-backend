package com.ivalo1.wechatbackend.controller;

import com.ivalo1.wechatbackend.common.ApiResponse;
import com.ivalo1.wechatbackend.dto.LoginRequest;
import com.ivalo1.wechatbackend.dto.LoginResponse;
import com.ivalo1.wechatbackend.model.User;
import com.ivalo1.wechatbackend.service.UserService;
import com.ivalo1.wechatbackend.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest req) {
        // In production: exchange `code` with WeChat server to get openId
        if (req.getCode() == null || req.getCode().isEmpty()) {
            return ApiResponse.error("missing code");
        }
        User user = userService.findOrCreateByOpenId(req.getCode());
        String token = jwtUtil.generateToken(user);
        LoginResponse resp = new LoginResponse();
        resp.setToken(token);
        resp.setUser(user);
        return ApiResponse.ok(resp);
    }
}
