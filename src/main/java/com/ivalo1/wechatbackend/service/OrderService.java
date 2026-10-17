package com.ivalo1.wechatbackend.service;

import com.ivalo1.wechatbackend.mapper.OrderMapper;
import com.ivalo1.wechatbackend.model.Order;
import com.ivalo1.wechatbackend.model.OrderItem;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderMapper orderMapper;

    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public Order createOrder(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("CREATED");
        orderMapper.insertOrder(order);
        // insert items
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                item.setOrderId(order.getId());
                orderMapper.insertOrderItem(item);
            }
        }
        return order;
    }

    public List<Order> listByUser(Long userId) {
        return orderMapper.findByUserId(userId);
    }

    public void updateOrderStatus(Long orderId, String status) {
        orderMapper.updateOrderStatus(orderId, status);
    }

    public Order findById(Long id) {
        return orderMapper.findById(id);
    }
}
