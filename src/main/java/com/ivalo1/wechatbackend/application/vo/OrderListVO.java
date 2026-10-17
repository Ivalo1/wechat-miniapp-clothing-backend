package com.ivalo1.wechatbackend.application.vo;
import lombok.Data;
import java.math.BigDecimal; import java.time.LocalDateTime;
@Data
public class OrderListVO { private Long id; private String orderNo; private BigDecimal payAmount; private Integer status; private String statusText; private LocalDateTime createTime; }
