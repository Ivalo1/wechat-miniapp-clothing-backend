package com.ivalo1.wechatbackend.application.service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivalo1.wechatbackend.common.*;
import com.ivalo1.wechatbackend.domain.order.*;
import com.ivalo1.wechatbackend.infrastructure.config.WxPayProperties;
import com.ivalo1.wechatbackend.infrastructure.lock.RedisDistributedLock;
import com.ivalo1.wechatbackend.infrastructure.mq.MessageProducer;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime; import java.util.*;

@Slf4j
@Service @RequiredArgsConstructor
public class PaymentAppService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final WxPayProperties wxPayProperties;
    private final RedisDistributedLock distributedLock;
    private final MessageProducer messageProducer;

    public Map<String, String> createPayment(Long userId, String orderNo) {
        Order order = orderRepository.findByOrderNo(orderNo);
        if (order == null || !order.getUserId().equals(userId)) throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        if (!order.isPendingPayment()) throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR);
        Map<String, String> result = new HashMap<>();
        result.put("orderNo", orderNo); result.put("payAmount", order.getPayAmount().toString());
        result.put("status", "mock"); // 实际调用微信支付
        return result;
    }

    @Transactional
    public String handlePayNotify(String xmlData) {
        // 简化实现
        return "OK";
    }
}
