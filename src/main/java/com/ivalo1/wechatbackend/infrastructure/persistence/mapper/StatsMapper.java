package com.ivalo1.wechatbackend.infrastructure.persistence.mapper;

import org.apache.ibatis.annotations.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 统计报表 Mapper
 * 直接写 SQL 聚合查询，不经过实体映射
 */
@Mapper
public interface StatsMapper {

    /** 今日销售额 (已支付订单) */
    @Select("SELECT COALESCE(SUM(pay_amount), 0) FROM t_order WHERE status >= 1 AND status <= 4 AND pay_time >= CURRENT_DATE")
    BigDecimal todaySales();

    /** 今日订单数 (所有状态) */
    @Select("SELECT COUNT(*) FROM t_order WHERE create_time >= CURRENT_DATE")
    Integer todayOrders();

    /** 今日新增用户 */
    @Select("SELECT COUNT(*) FROM \"user\" WHERE create_time >= CURRENT_DATE AND deleted = 0")
    Integer todayNewUsers();

    /** 本月销售额 */
    @Select("SELECT COALESCE(SUM(pay_amount), 0) FROM t_order WHERE status >= 1 AND status <= 4 AND pay_time >= date_trunc('month', CURRENT_DATE)")
    BigDecimal monthSales();

    /** 本月订单数 */
    @Select("SELECT COUNT(*) FROM t_order WHERE create_time >= date_trunc('month', CURRENT_DATE)")
    Integer monthOrders();

    /** 总用户数 */
    @Select("SELECT COUNT(*) FROM \"user\" WHERE deleted = 0 AND status = 1")
    Integer totalUsers();

    /** 总商品数 (上架) */
    @Select("SELECT COUNT(*) FROM t_product WHERE status = 1 AND deleted = 0")
    Integer totalProducts();

    /** 待发货订单数 */
    @Select("SELECT COUNT(*) FROM t_order WHERE status = 1 AND deleted = 0")
    Integer pendingShipOrders();

    /** 待处理退款单数 */
    @Select("SELECT COUNT(*) FROM t_refund WHERE status = 0 AND deleted = 0")
    Integer pendingRefunds();

    /** 商品销量排行 TOP 10 */
    @Select("""
        SELECT oi.product_id, oi.product_name, SUM(oi.quantity) AS sales_count, SUM(oi.subtotal) AS sales_amount
        FROM t_order_item oi
        JOIN t_order o ON o.id = oi.order_id
        WHERE o.status >= 1 AND o.status <= 4 AND o.deleted = 0
        GROUP BY oi.product_id, oi.product_name
        ORDER BY sales_count DESC
        LIMIT 10
    """)
    @MapKey("product_id")
    List<Map<String, Object>> productRank();

    /** 分类销量排行 */
    @Select("""
        SELECT p.category_id, c.name AS category_name, SUM(oi.quantity) AS sales_count
        FROM t_order_item oi
        JOIN t_order o ON o.id = oi.order_id
        JOIN t_product p ON p.id = oi.product_id
        JOIN t_category c ON c.id = p.category_id
        WHERE o.status >= 1 AND o.status <= 4 AND o.deleted = 0
        GROUP BY p.category_id, c.name
        ORDER BY sales_count DESC
        LIMIT 10
    """)
    List<Map<String, Object>> categoryRank();

    /** 近7天每日销售趋势 */
    @Select("""
        SELECT DATE(pay_time)::text AS date, SUM(pay_amount) AS sales_amount, COUNT(*) AS order_count
        FROM t_order
        WHERE status >= 1 AND status <= 4
          AND pay_time >= CURRENT_DATE - INTERVAL '6 days'
        GROUP BY DATE(pay_time)
        ORDER BY date ASC
    """)
    List<Map<String, Object>> dailySales7d();

    /** 近30天每日销售趋势 */
    @Select("""
        SELECT DATE(pay_time)::text AS date, SUM(pay_amount) AS sales_amount, COUNT(*) AS order_count
        FROM t_order
        WHERE status >= 1 AND status <= 4
          AND pay_time >= CURRENT_DATE - INTERVAL '29 days'
        GROUP BY DATE(pay_time)
        ORDER BY date ASC
    """)
    List<Map<String, Object>> dailySales30d();

    /** 订单状态分布 */
    @Select("""
        SELECT status, COUNT(*) AS count FROM t_order WHERE deleted = 0 GROUP BY status ORDER BY status
    """)
    List<Map<String, Object>> orderStatusDistribution();
}
