package com.ivalo1.wechatbackend.application.vo;
import lombok.Data;
import java.math.BigDecimal;
@Data
public class MemberInfoVO { private String nickname; private String avatar; private Integer points; private String levelName; private BigDecimal totalConsumed; private Boolean signedToday; }
