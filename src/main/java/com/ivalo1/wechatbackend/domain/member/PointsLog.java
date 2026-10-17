package com.ivalo1.wechatbackend.domain.member;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_points_log")
public class PointsLog {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long userId;
    private Integer type;
    private Integer points;
    private String description;
    private LocalDateTime createTime;
}
