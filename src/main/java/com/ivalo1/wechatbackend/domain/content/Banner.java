package com.ivalo1.wechatbackend.domain.content;
import com.ivalo1.wechatbackend.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_banner")
public class Banner extends BaseEntity {
    private String title;
    private String imageUrl;
    private String linkType;   // product/category/url
    private String linkValue;
    private Integer sortOrder;
    private Integer status;    // 0隐藏 1显示
    private Integer position;  // 1首页 2分类页
}
