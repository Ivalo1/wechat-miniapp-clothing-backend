package com.ivalo1.wechatbackend.domain.product;

import com.ivalo1.wechatbackend.common.BaseEntity;
import com.ivalo1.wechatbackend.common.BusinessException;
import com.ivalo1.wechatbackend.common.ErrorCode;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品聚合根
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_product")
public class Product extends BaseEntity {

    private String name;
    private String description;
    private BigDecimal price;
    private String mainImage;
    private String images;
    private Long categoryId;
    private Integer status; // 0下架 1上架
    private Integer sales;
    private Integer sortOrder;

    // ========== 领域行为 ==========

    /** 上架 */
    public void onShelf() {
        this.status = 1;
        this.setUpdateTime(java.time.LocalDateTime.now());
    }

    /** 下架 */
    public void offShelf() {
        this.status = 0;
        this.setUpdateTime(java.time.LocalDateTime.now());
    }

    /** 是否上架 */
    public boolean isOnShelf() {
        return this.status != null && this.status == 1;
    }

    /** 增加销量 */
    public void addSales(int quantity) {
        this.sales = (this.sales != null ? this.sales : 0) + quantity;
    }
}
