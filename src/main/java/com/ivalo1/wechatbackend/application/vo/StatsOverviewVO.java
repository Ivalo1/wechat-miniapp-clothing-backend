package com.ivalo1.wechatbackend.application.vo;
import lombok.Data;
import java.math.BigDecimal;
@Data
public class StatsOverviewVO { private Long todayOrders; private BigDecimal todayAmount; private Long totalUsers; private Long todayNewUsers; }
