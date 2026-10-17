package com.ivalo1.wechatbackend.domain.share;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_share_record")
public class ShareRecord {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long userId;        // 分享者
    private Long targetUserId;  // 被分享者
    private Long productId;
    private String shareType;   // product/activity
    private String shareCode;   // 分享码
    private Integer clickCount;
    private Integer convertCount;
    private BigDecimal commission;
    private LocalDateTime createTime;
}
