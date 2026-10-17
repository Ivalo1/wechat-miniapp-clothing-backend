package com.ivalo1.wechatbackend.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivalo1.wechatbackend.domain.auth.AdminUser;
import com.ivalo1.wechatbackend.domain.auth.AdminUserRepository;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.AdminUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminUserRepositoryImpl implements AdminUserRepository {

    private final AdminUserMapper adminUserMapper;

    @Override
    public AdminUser findByUsername(String username) {
        return adminUserMapper.selectOne(new LambdaQueryWrapper<AdminUser>()
                .eq(AdminUser::getUsername, username));
    }
}
