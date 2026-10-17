package com.ivalo1.wechatbackend.domain.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_product_tag")
public class ProductTag {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long productId;
    private String tagName;
    private LocalDateTime createTime;
}
