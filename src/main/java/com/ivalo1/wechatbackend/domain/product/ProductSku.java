package com.ivalo1.wechatbackend.domain.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ivalo1.wechatbackend.common.BusinessException;
import com.ivalo1.wechatbackend.common.ErrorCode;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品SKU实体 (属于 Product 聚合)
 */
@Data
@TableName("t_product_sku")
public class ProductSku {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long productId;
    private String skuName;
    private String color;
    private String size;
    private BigDecimal price;
    private Integer stock;
    private String image;
    private Integer status; // 0禁用 1启用
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    /** 扣减库存 */
    public void deductStock(int quantity) {
        if (this.stock == null || this.stock < quantity) {
            throw new BusinessException(ErrorCode.STOCK_NOT_ENOUGH,
                    "商品 " + this.skuName + " 库存不足");
        }
        this.stock -= quantity;
    }

    /** 恢复库存 */
    public void restoreStock(int quantity) {
        this.stock = (this.stock != null ? this.stock : 0) + quantity;
    }

    /** 是否可用 */
    public boolean isAvailable() {
        return this.status != null && this.status == 1;
    }
}
