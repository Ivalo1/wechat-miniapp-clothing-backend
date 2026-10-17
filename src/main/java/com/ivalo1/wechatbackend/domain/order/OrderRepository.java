package com.ivalo1.wechatbackend.domain.order;

/**
 * 订单仓储接口
 */
public interface OrderRepository {
    Order findByOrderNo(String orderNo);
    java.util.List<Order> findByUserId(Long userId, Integer status);
    void save(Order order);
    void update(Order order);
}
