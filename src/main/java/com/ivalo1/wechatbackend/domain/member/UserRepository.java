package com.ivalo1.wechatbackend.domain.member;

/**
 * 用户仓储接口
 */
public interface UserRepository {
    User findById(Long id);
    User findByOpenid(String openid);
    void save(User user);
    void update(User user);
}
