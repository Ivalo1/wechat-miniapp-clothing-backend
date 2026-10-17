package com.ivalo1.wechatbackend.infrastructure.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProducer {

    private final RocketMQTemplate rocketMQTemplate;

    public void sendOrderDelayCancel(String orderNo) {
        log.info("发送订单延迟取消消息: {}", orderNo);
        Message<String> message = MessageBuilder.withPayload(orderNo).build();
        rocketMQTemplate.syncSend(MQConstants.ORDER_TOPIC + ":" + MQConstants.TAG_ORDER_CANCELLED, message, 3000, 16);
    }

    public void sendOrderPaidEvent(String orderNo) {
        log.info("发送订单已支付事件: {}", orderNo);
        rocketMQTemplate.convertAndSend(MQConstants.ORDER_TOPIC + ":" + MQConstants.TAG_ORDER_PAID, orderNo);
    }

    public void sendOrderCancelledEvent(String orderNo) {
        log.info("发送订单取消事件: {}", orderNo);
        rocketMQTemplate.convertAndSend(MQConstants.ORDER_TOPIC + ":" + MQConstants.TAG_ORDER_CANCELLED, orderNo);
    }

    public void sendPointsAddEvent(String message) {
        log.info("发送积分增加事件: {}", message);
        rocketMQTemplate.convertAndSend(MQConstants.POINTS_TOPIC + ":" + MQConstants.TAG_POINTS_ADD, message);
    }
}
