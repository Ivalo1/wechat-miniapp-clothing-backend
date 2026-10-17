package com.ivalo1.wechatbackend.domain.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单明细实体 (属于 Order 聚合)
 */
@Data
@TableName("t_order_item")
public class OrderItem {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long orderId;
    private Long productId;
    private Long skuId;
    private String productName;
    private String skuName;
    private String productImage;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subtotal;
    private LocalDateTime createTime;

    /** 计算小计 */
    public void calculateSubtotal() {
        this.subtotal = this.price.multiply(BigDecimal.valueOf(this.quantity));
    }
}
