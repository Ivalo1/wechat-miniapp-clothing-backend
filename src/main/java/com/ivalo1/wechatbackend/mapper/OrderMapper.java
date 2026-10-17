package com.ivalo1.wechatbackend.mapper;

import com.ivalo1.wechatbackend.model.Order;
import com.ivalo1.wechatbackend.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    int insertOrder(Order order);
    List<Order> findByUserId(Long userId);
    int insertOrderItem(OrderItem item);
    int updateOrderStatus(@Param("orderId") Long orderId, @Param("status") String status);
    Order findById(@Param("id") Long id);
}
