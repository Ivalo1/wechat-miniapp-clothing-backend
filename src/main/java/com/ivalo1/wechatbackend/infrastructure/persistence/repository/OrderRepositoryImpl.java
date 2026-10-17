package com.ivalo1.wechatbackend.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivalo1.wechatbackend.domain.order.Order;
import com.ivalo1.wechatbackend.domain.order.OrderRepository;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderMapper orderMapper;

    @Override
    public Order findByOrderNo(String orderNo) {
        return orderMapper.selectOne(new LambdaQueryWrapper<Order>().eq(Order::getOrderNo, orderNo));
    }

    @Override
    public List<Order> findByUserId(Long userId, Integer status) {
        LambdaQueryWrapper<Order> w = new LambdaQueryWrapper<Order>().eq(Order::getUserId, userId);
        if (status != null) w.eq(Order::getStatus, status);
        w.orderByDesc(Order::getCreateTime);
        return orderMapper.selectList(w);
    }

    @Override
    public void save(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public void update(Order order) {
        orderMapper.updateById(order);
    }
}
