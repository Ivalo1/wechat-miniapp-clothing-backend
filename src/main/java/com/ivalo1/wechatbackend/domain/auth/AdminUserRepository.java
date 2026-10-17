package com.ivalo1.wechatbackend.domain.auth;

public interface AdminUserRepository {
    AdminUser findByUsername(String username);
}
