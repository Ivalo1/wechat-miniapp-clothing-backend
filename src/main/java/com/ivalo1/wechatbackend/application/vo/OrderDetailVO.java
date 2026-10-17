package com.ivalo1.wechatbackend.application.vo;
import lombok.Data;
import java.math.BigDecimal; import java.time.LocalDateTime; import java.util.List;
@Data
public class OrderDetailVO { private Long id; private String orderNo; private BigDecimal totalAmount; private BigDecimal discountAmount; private BigDecimal payAmount; private Integer status; private String statusText; private String addressSnapshot; private String remark; private LocalDateTime createTime; private LocalDateTime payTime; private List<ItemVO> items;
    @Data public static class ItemVO { private Long productId; private Long skuId; private String productName; private String skuName; private String productImage; private BigDecimal price; private Integer quantity; }
}
