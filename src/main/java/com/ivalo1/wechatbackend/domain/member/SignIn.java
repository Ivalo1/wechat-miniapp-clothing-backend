package com.ivalo1.wechatbackend.domain.member;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_sign_in")
public class SignIn {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long userId;
    private java.time.LocalDate signDate;
    private Integer pointsEarned;
    private LocalDateTime createTime;
}
