package com.ivalo1.wechatbackend.mapper;

import com.ivalo1.wechatbackend.model.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    int insertOrder(Order order);
    List<Order> findByUserId(Long userId);
}
