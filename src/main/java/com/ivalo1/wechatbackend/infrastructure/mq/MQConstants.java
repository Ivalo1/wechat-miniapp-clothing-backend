package com.ivalo1.wechatbackend.infrastructure.mq;

public final class MQConstants {
    private MQConstants() {}
    public static final String ORDER_TOPIC = "ORDER_TOPIC";
    public static final String TAG_ORDER_CREATED = "ORDER_CREATED";
    public static final String TAG_ORDER_PAID = "ORDER_PAID";
    public static final String TAG_ORDER_CANCELLED = "ORDER_CANCELLED";
    public static final String POINTS_TOPIC = "POINTS_TOPIC";
    public static final String TAG_POINTS_ADD = "POINTS_ADD";
    public static final String CG_ORDER = "CG_ORDER";
    public static final String CG_POINTS = "CG_POINTS";
}
