package com.ivalo1.wechatbackend.infrastructure.mq;

import com.ivalo1.wechatbackend.domain.order.Order;
import com.ivalo1.wechatbackend.domain.order.OrderItem;
import com.ivalo1.wechatbackend.domain.order.OrderRepository;
import com.ivalo1.wechatbackend.domain.order.OrderStatus;
import com.ivalo1.wechatbackend.domain.product.ProductRepository;
import com.ivalo1.wechatbackend.domain.product.ProductSku;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageConsumer {

    @Component
    @RequiredArgsConstructor
    @RocketMQMessageListener(topic = MQConstants.ORDER_TOPIC, selectorExpression = MQConstants.TAG_ORDER_CANCELLED, consumerGroup = MQConstants.CG_ORDER)
    public static class OrderCancelConsumer implements RocketMQListener<String> {
        private final OrderRepository orderRepository;
        private final ProductRepository productRepository;

        @Override
        public void onMessage(String orderNo) {
            try {
                log.info("收到订单取消消息: {}", orderNo);
                Order order = orderRepository.findByOrderNo(orderNo);
                if (order != null && order.isPendingPayment()) {
                    order.timeoutCancel();
                    orderRepository.update(order);
                    log.info("订单超时取消成功: {}", orderNo);
                }
            } catch (Exception e) {
                log.error("处理订单取消消息失败: {}", orderNo, e);
                throw e;
            }
        }
    }

    @Component
    @RocketMQMessageListener(topic = MQConstants.ORDER_TOPIC, selectorExpression = MQConstants.TAG_ORDER_PAID, consumerGroup = MQConstants.CG_ORDER + "_PAID")
    public static class OrderPaidConsumer implements RocketMQListener<String> {
        @Override
        public void onMessage(String orderNo) {
            log.info("订单支付成功事件处理: {}", orderNo);
            // 异步: 加积分、更新统计、发通知
        }
    }
}
