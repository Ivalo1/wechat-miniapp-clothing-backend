package com.ivalo1.wechatbackend.application.vo;
import lombok.Data;
import java.math.BigDecimal;
@Data
public class ProductListVO { private Long id; private String name; private BigDecimal price; private String mainImage; private Integer sales; private Integer status; }
