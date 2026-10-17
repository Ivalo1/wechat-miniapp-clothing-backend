package com.ivalo1.wechatbackend.application.service;

import com.ivalo1.wechatbackend.application.vo.StatsVO;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.StatsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 统计报表应用服务
 * 所有数据从数据库实时聚合，缓存 5 分钟
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StatsAppService {

    private final StatsMapper statsMapper;

    /**
     * 管理端数据概览
     */
    @Cacheable(value = "stats", key = "'dashboard'")
    public StatsVO getDashboardStats() {
        StatsVO stats = new StatsVO();

        // ===== 概览数据 =====
        stats.setTodaySales(safeDecimal(statsMapper.todaySales()));
        stats.setTodayOrders(safeInt(statsMapper.todayOrders()));
        stats.setTodayNewUsers(safeInt(statsMapper.todayNewUsers()));
        stats.setMonthSales(safeDecimal(statsMapper.monthSales()));
        stats.setMonthOrders(safeInt(statsMapper.monthOrders()));
        stats.setTotalUsers(safeInt(statsMapper.totalUsers()));
        stats.setTotalProducts(safeInt(statsMapper.totalProducts()));

        // ===== 待办 =====
        stats.setPendingShipOrders(safeInt(statsMapper.pendingShipOrders()));
        stats.setPendingRefunds(safeInt(statsMapper.pendingRefunds()));

        // ===== 商品销量排行 =====
        stats.setProductRank(statsMapper.productRank().stream().map(m -> {
            StatsVO.ProductRankVO vo = new StatsVO.ProductRankVO();
            vo.setProductId(toLong(m.get("product_id")));
            vo.setProductName(toStr(m.get("product_name")));
            vo.setSalesCount(toInt(m.get("sales_count")));
            vo.setSalesAmount(toDecimal(m.get("sales_amount")));
            return vo;
        }).collect(Collectors.toList()));

        // ===== 分类销量排行 =====
        stats.setCategoryRank(statsMapper.categoryRank().stream().map(m -> {
            StatsVO.CategoryRankVO vo = new StatsVO.CategoryRankVO();
            vo.setCategoryId(toLong(m.get("category_id")));
            vo.setCategoryName(toStr(m.get("category_name")));
            vo.setSalesCount(toInt(m.get("sales_count")));
            return vo;
        }).collect(Collectors.toList()));

        // ===== 近7天销售趋势 =====
        stats.setDailySales(statsMapper.dailySales7d().stream().map(m -> {
            StatsVO.DailySalesVO vo = new StatsVO.DailySalesVO();
            vo.setDate(toStr(m.get("date")));
            vo.setSalesAmount(toDecimal(m.get("sales_amount")));
            vo.setOrderCount(toInt(m.get("order_count")));
            return vo;
        }).collect(Collectors.toList()));

        // ===== 订单状态分布 =====
        stats.setOrderStatusDistribution(statsMapper.orderStatusDistribution());

        return stats;
    }

    /**
     * 近30天销售趋势 (单独接口，数据量大)
     */
    @Cacheable(value = "stats", key = "'trend30d'")
    public List<StatsVO.DailySalesVO> getDailySales30d() {
        return statsMapper.dailySales30d().stream().map(m -> {
            StatsVO.DailySalesVO vo = new StatsVO.DailySalesVO();
            vo.setDate(toStr(m.get("date")));
            vo.setSalesAmount(toDecimal(m.get("sales_amount")));
            vo.setOrderCount(toInt(m.get("order_count")));
            return vo;
        }).collect(Collectors.toList());
    }

    // ===== 类型安全转换 (MyBatis Map结果可能是 Number/BigDecimal 等) =====

    private BigDecimal safeDecimal(Object val) {
        if (val == null) return BigDecimal.ZERO;
        if (val instanceof BigDecimal bd) return bd;
        return new BigDecimal(val.toString());
    }

    private Integer safeInt(Object val) {
        if (val == null) return 0;
        if (val instanceof Integer i) return i;
        if (val instanceof Number n) return n.intValue();
        return Integer.parseInt(val.toString());
    }

    private BigDecimal toDecimal(Object val) {
        if (val == null) return BigDecimal.ZERO;
        if (val instanceof BigDecimal bd) return bd;
        return new BigDecimal(val.toString());
    }

    private Integer toInt(Object val) {
        if (val == null) return 0;
        if (val instanceof Integer i) return i;
        if (val instanceof Number n) return n.intValue();
        return Integer.parseInt(val.toString());
    }

    private Long toLong(Object val) {
        if (val == null) return 0L;
        if (val instanceof Long l) return l;
        if (val instanceof Number n) return n.longValue();
        return Long.parseLong(val.toString());
    }

    private String toStr(Object val) {
        return val != null ? val.toString() : "";
    }
}
