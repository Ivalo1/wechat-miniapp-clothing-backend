package com.ivalo1.wechatbackend.domain.product;

import com.ivalo1.wechatbackend.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_category")
public class Category extends BaseEntity {
    private String name;
    private Long parentId;
    private Integer level;
    private Integer sortOrder;
    private String icon;
    private Integer status;
}
