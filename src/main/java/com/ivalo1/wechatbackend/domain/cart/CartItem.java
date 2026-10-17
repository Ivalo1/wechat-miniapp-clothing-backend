package com.ivalo1.wechatbackend.domain.cart;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_cart_item")
public class CartItem {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long userId;
    private Long productId;
    private Long skuId;
    private Integer quantity;
    private Integer selected;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public void updateQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("数量必须大于0");
        this.quantity = quantity;
        this.updateTime = LocalDateTime.now();
    }
}
