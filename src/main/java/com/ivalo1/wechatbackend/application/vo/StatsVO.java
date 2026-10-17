package com.ivalo1.wechatbackend.application.vo;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class StatsVO {
    // ===== 概览 =====
    private BigDecimal todaySales;
    private Integer todayOrders;
    private Integer todayNewUsers;
    private BigDecimal monthSales;
    private Integer monthOrders;
    private Integer totalUsers;
    private Integer totalProducts;

    // ===== 待办 =====
    private Integer pendingShipOrders;
    private Integer pendingRefunds;

    // ===== 排行 =====
    private List<ProductRankVO> productRank;
    private List<CategoryRankVO> categoryRank;

    // ===== 趋势 =====
    private List<DailySalesVO> dailySales;

    // ===== 订单状态分布 =====
    private List<Map<String, Object>> orderStatusDistribution;

    @Data
    public static class ProductRankVO {
        private Long productId;
        private String productName;
        private Integer salesCount;
        private BigDecimal salesAmount;
    }

    @Data
    public static class CategoryRankVO {
        private Long categoryId;
        private String categoryName;
        private Integer salesCount;
    }

    @Data
    public static class DailySalesVO {
        private String date;
        private BigDecimal salesAmount;
        private Integer orderCount;
    }
}
