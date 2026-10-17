package com.ivalo1.wechatbackend.mapper;

import com.ivalo1.wechatbackend.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findById(@Param("id") Long id);
    User findByOpenId(@Param("openId") String openId);
    int insert(User user);
}
