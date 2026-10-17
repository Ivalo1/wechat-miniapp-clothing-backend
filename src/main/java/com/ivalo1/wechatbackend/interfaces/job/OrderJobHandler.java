package com.ivalo1.wechatbackend.interfaces.job;
import com.ivalo1.wechatbackend.domain.order.Order;
import com.ivalo1.wechatbackend.domain.order.OrderItem;
import com.ivalo1.wechatbackend.domain.order.OrderRepository;
import com.ivalo1.wechatbackend.domain.order.OrderStatus;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.OrderItemMapper;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.ProductSkuMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j @Component @RequiredArgsConstructor
public class OrderJobHandler {
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;
    private final ProductSkuMapper productSkuMapper;

    @XxlJob("orderTimeoutCancelJob")
    public void orderTimeoutCancelJob() {
        log.info("执行订单超时取消任务");
        // TODO: 查询超时订单并取消
    }

    @XxlJob("autoConfirmReceiveJob")
    public void autoConfirmReceiveJob() {
        log.info("执行自动确认收货任务");
    }
}
