package com.ivalo1.wechatbackend.domain.content;
import com.ivalo1.wechatbackend.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_announcement")
public class Announcement extends BaseEntity {
    private String title;
    private String content;
    private Integer status;
    private LocalDateTime publishTime;
}
