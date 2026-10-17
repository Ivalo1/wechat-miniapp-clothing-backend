package com.ivalo1.wechatbackend.application.vo;
import lombok.Data;
import java.math.BigDecimal;
@Data
public class CartVO { private Long id; private Long productId; private Long skuId; private String productName; private String skuName; private String productImage; private BigDecimal price; private Integer quantity; private Integer selected; }
