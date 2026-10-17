package com.ivalo1.wechatbackend.domain.review;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("t_product_review")
public class ProductReview {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long userId;
    private Long productId;
    private Long orderId;
    private Long orderItemId;
    private String nickname;
    private String avatar;
    private Integer rating;      // 1-5星
    private String content;
    private String images;       // JSON
    private String skuName;
    private Boolean isAnonymous;
    private Integer replyStatus; // 0未回复 1已回复
    private String replyContent;
    private LocalDateTime replyTime;
    private LocalDateTime createTime;

    public static ProductReview create(Long userId, Long productId, Long orderId, Long orderItemId,
                                        String nickname, String avatar, int rating, String content, String images, String skuName) {
        ProductReview r = new ProductReview();
        r.userId = userId; r.productId = productId; r.orderId = orderId; r.orderItemId = orderItemId;
        r.nickname = nickname; r.avatar = avatar; r.rating = rating;
        r.content = content; r.images = images; r.skuName = skuName;
        r.isAnonymous = false; r.replyStatus = 0;
        r.createTime = LocalDateTime.now();
        return r;
    }
}
