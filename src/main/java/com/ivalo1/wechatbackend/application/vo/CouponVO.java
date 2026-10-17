package com.ivalo1.wechatbackend.application.vo;
import lombok.Data;
import java.math.BigDecimal; import java.time.LocalDateTime;
@Data
public class CouponVO { private Long id; private String name; private Integer type; private BigDecimal discountAmount; private BigDecimal discountRate; private BigDecimal minAmount; private LocalDateTime expireTime; private Integer status; }
