package com.ivalo1.wechatbackend.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivalo1.wechatbackend.domain.member.User;
import com.ivalo1.wechatbackend.domain.member.UserRepository;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public User findById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User findByOpenid(String openid) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getOpenid, openid));
    }

    @Override
    public void save(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }
}
