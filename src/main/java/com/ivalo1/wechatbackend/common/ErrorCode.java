package com.ivalo1.wechatbackend.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 通用
    SUCCESS(0, "success"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "无权限"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_ERROR(500, "服务器内部错误"),

    // 认证 1xxx
    WX_LOGIN_FAIL(1001, "微信登录失败"),
    TOKEN_EXPIRED(1002, "Token已过期"),
    TOKEN_INVALID(1003, "Token无效"),
    ADMIN_LOGIN_FAIL(1004, "管理员登录失败"),

    // 用户 2xxx
    USER_NOT_FOUND(2001, "用户不存在"),
    USER_DISABLED(2002, "用户已禁用"),

    // 商品 3xxx
    PRODUCT_NOT_FOUND(3001, "商品不存在"),
    PRODUCT_OFF_SHELF(3002, "商品已下架"),
    SKU_NOT_FOUND(3003, "SKU不存在"),
    STOCK_NOT_ENOUGH(3004, "库存不足"),
    CATEGORY_NOT_FOUND(3005, "分类不存在"),

    // 购物车 4xxx
    CART_ITEM_NOT_FOUND(4001, "购物车项不存在"),

    // 订单 5xxx
    ORDER_NOT_FOUND(5001, "订单不存在"),
    ORDER_STATUS_ERROR(5002, "订单状态异常"),
    ORDER_ALREADY_PAID(5003, "订单已支付"),
    ORDER_CANNOT_CANCEL(5004, "订单不可取消"),

    // 支付 6xxx
    PAY_CREATE_FAIL(6001, "创建支付失败"),

    // 地址 7xxx
    ADDRESS_NOT_FOUND(7001, "地址不存在"),

    // 会员 8xxx
    ALREADY_SIGNED_TODAY(8001, "今日已签到"),
    POINTS_NOT_ENOUGH(8002, "积分不足"),

    // 优惠券 9xxx
    COUPON_NOT_FOUND(9001, "优惠券不存在"),
    COUPON_EXPIRED(9002, "优惠券已过期"),
    COUPON_NOT_AVAILABLE(9003, "优惠券不可用"),
    COUPON_ALREADY_RECEIVED(9004, "已领取过该优惠券");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
