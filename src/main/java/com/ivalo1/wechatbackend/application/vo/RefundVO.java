package com.ivalo1.wechatbackend.application.vo;
import lombok.Data;
import java.math.BigDecimal; import java.time.LocalDateTime;
@Data
public class RefundVO { private Long id; private String refundNo; private String orderNo; private Integer type; private String typeText; private Integer status; private String statusText; private String reason; private BigDecimal refundAmount; private String rejectReason; private LocalDateTime createTime; private LocalDateTime refundTime; }
