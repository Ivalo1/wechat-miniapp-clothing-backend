package com.ivalo1.wechatbackend.domain.recommend;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_user_behavior")
public class UserBehavior {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long userId;
    private Long productId;
    private String behaviorType; // view/cart/favorite/purchase
    private LocalDateTime createTime;
}
