-- ========================================
-- 服装订购小程序 - 数据库初始化脚本
-- PostgreSQL
-- ========================================

-- 用户表
CREATE TABLE IF NOT EXISTS "user" (
    id              BIGSERIAL       PRIMARY KEY,
    openid          VARCHAR(64)     NOT NULL UNIQUE,
    union_id        VARCHAR(64),
    nickname        VARCHAR(64)     DEFAULT '',
    avatar_url      VARCHAR(512)    DEFAULT '',
    phone           VARCHAR(20),
    gender          SMALLINT        DEFAULT 0 COMMENT '0-未知 1-男 2-女',
    member_level    SMALLINT        DEFAULT 0 COMMENT '会员等级: 0-普通 1-银卡 2-金卡 3-钻石',
    points          INTEGER         DEFAULT 0,
    total_spent     DECIMAL(12,2)   DEFAULT 0.00,
    last_sign_date  DATE,
    status          SMALLINT        DEFAULT 1 COMMENT '0-禁用 1-正常',
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    deleted         SMALLINT        DEFAULT 0
);
CREATE INDEX idx_user_openid ON "user"(openid);
CREATE INDEX idx_user_phone ON "user"(phone);

-- 管理员表
CREATE TABLE IF NOT EXISTS admin_user (
    id              BIGSERIAL       PRIMARY KEY,
    username        VARCHAR(64)     NOT NULL UNIQUE,
    password_hash   VARCHAR(256)    NOT NULL,
    real_name       VARCHAR(64)     DEFAULT '',
    role            VARCHAR(32)     DEFAULT 'ADMIN',
    status          SMALLINT        DEFAULT 1 COMMENT '0-禁用 1-正常',
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    deleted         SMALLINT        DEFAULT 0
);
-- 默认管理员 (密码: admin123)
INSERT INTO admin_user (username, password_hash, real_name, role)
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '超级管理员', 'SUPER_ADMIN')
ON CONFLICT DO NOTHING;

-- 商品分类表
CREATE TABLE IF NOT EXISTS category (
    id              BIGSERIAL       PRIMARY KEY,
    parent_id       BIGINT          DEFAULT 0 COMMENT '父分类ID, 0为顶级',
    name            VARCHAR(64)     NOT NULL,
    icon            VARCHAR(512)    DEFAULT '',
    sort_order      INTEGER         DEFAULT 0,
    level           SMALLINT        DEFAULT 1 COMMENT '层级: 1-一级 2-二级',
    status          SMALLINT        DEFAULT 1 COMMENT '0-禁用 1-启用',
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    deleted         SMALLINT        DEFAULT 0
);
CREATE INDEX idx_category_parent ON category(parent_id);

-- 商品SPU表
CREATE TABLE IF NOT EXISTS product (
    id              BIGSERIAL       PRIMARY KEY,
    category_id     BIGINT          NOT NULL,
    name            VARCHAR(128)    NOT NULL,
    subtitle        VARCHAR(256)    DEFAULT '',
    main_image      VARCHAR(512)    NOT NULL DEFAULT '',
    images          TEXT            DEFAULT '[]',
    description     TEXT            DEFAULT '',
    detail          TEXT            DEFAULT '',
    price_min       DECIMAL(10,2)   DEFAULT 0.00,
    price_max       DECIMAL(10,2)   DEFAULT 0.00,
    sales           INTEGER         DEFAULT 0,
    status          SMALLINT        DEFAULT 0 COMMENT '0-下架 1-上架',
    sort_order      INTEGER         DEFAULT 0,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    deleted         SMALLINT        DEFAULT 0
);
CREATE INDEX idx_product_category ON product(category_id);
CREATE INDEX idx_product_status ON product(status);

-- 商品SKU表
CREATE TABLE IF NOT EXISTS product_sku (
    id              BIGSERIAL       PRIMARY KEY,
    product_id      BIGINT          NOT NULL,
    sku_name        VARCHAR(128)    NOT NULL DEFAULT '',
    color           VARCHAR(64)     NOT NULL DEFAULT '',
    color_image     VARCHAR(512)    DEFAULT '',
    size            VARCHAR(32)     NOT NULL DEFAULT '',
    stock           INTEGER         DEFAULT 0,
    price           DECIMAL(10,2)   NOT NULL DEFAULT 0.00,
    sku_code        VARCHAR(64)     DEFAULT '',
    status          SMALLINT        DEFAULT 1 COMMENT '0-禁用 1-启用',
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    deleted         SMALLINT        DEFAULT 0
);
CREATE INDEX idx_sku_product ON product_sku(product_id);

-- 商品标签表
CREATE TABLE IF NOT EXISTS product_tag (
    id              BIGSERIAL       PRIMARY KEY,
    product_id      BIGINT          NOT NULL,
    tag_name        VARCHAR(32)     NOT NULL,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_tag_product ON product_tag(product_id);

-- 收货地址表
CREATE TABLE IF NOT EXISTS address (
    id              BIGSERIAL       PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    receiver_name   VARCHAR(64)     NOT NULL,
    receiver_phone  VARCHAR(20)     NOT NULL,
    province        VARCHAR(32)     NOT NULL DEFAULT '',
    city            VARCHAR(32)     NOT NULL DEFAULT '',
    district        VARCHAR(32)     NOT NULL DEFAULT '',
    detail_address  VARCHAR(256)    NOT NULL DEFAULT '',
    is_default      BOOLEAN         DEFAULT FALSE,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    deleted         SMALLINT        DEFAULT 0
);
CREATE INDEX idx_address_user ON address(user_id);

-- 购物车表
CREATE TABLE IF NOT EXISTS cart_item (
    id              BIGSERIAL       PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    sku_id          BIGINT          NOT NULL,
    quantity        INTEGER         NOT NULL DEFAULT 1,
    selected        BOOLEAN         DEFAULT TRUE,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_cart_user ON cart_item(user_id);
CREATE UNIQUE INDEX idx_cart_user_sku ON cart_item(user_id, sku_id);

-- 订单主表
CREATE TABLE IF NOT EXISTS "order" (
    id                  BIGSERIAL       PRIMARY KEY,
    order_no            VARCHAR(32)     NOT NULL UNIQUE,
    user_id             BIGINT          NOT NULL,
    total_amount        DECIMAL(10,2)   NOT NULL DEFAULT 0.00,
    discount_amount     DECIMAL(10,2)   DEFAULT 0.00,
    freight_amount      DECIMAL(10,2)   DEFAULT 0.00,
    pay_amount          DECIMAL(10,2)   NOT NULL DEFAULT 0.00,
    status              SMALLINT        NOT NULL DEFAULT 0 COMMENT '0-待支付 1-已支付/待发货 2-已发货 3-已收货 4-已完成 5-已取消 6-超时取消 7-退款中 8-已退款',
    address_snapshot    TEXT            NOT NULL,
    coupon_id           BIGINT,
    remark              VARCHAR(256)    DEFAULT '',
    pay_time            TIMESTAMP,
    ship_time           TIMESTAMP,
    deliver_time        TIMESTAMP,
    complete_time       TIMESTAMP,
    cancel_time         TIMESTAMP,
    create_time         TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time         TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_order_user ON "order"(user_id);
CREATE INDEX idx_order_no ON "order"(order_no);
CREATE INDEX idx_order_status ON "order"(status);
CREATE INDEX idx_order_create_time ON "order"(create_time);

-- 订单明细表
CREATE TABLE IF NOT EXISTS order_item (
    id              BIGSERIAL       PRIMARY KEY,
    order_id        BIGINT          NOT NULL,
    sku_id          BIGINT          NOT NULL,
    product_name    VARCHAR(128)    NOT NULL DEFAULT '',
    sku_name        VARCHAR(128)    NOT NULL DEFAULT '',
    color           VARCHAR(64)     DEFAULT '',
    size            VARCHAR(32)     DEFAULT '',
    product_image   VARCHAR(512)    DEFAULT '',
    price           DECIMAL(10,2)   NOT NULL DEFAULT 0.00,
    quantity        INTEGER         NOT NULL DEFAULT 1,
    subtotal        DECIMAL(10,2)   NOT NULL DEFAULT 0.00
);
CREATE INDEX idx_order_item_order ON order_item(order_id);

-- 会员等级配置表
CREATE TABLE IF NOT EXISTS member_level (
    id              BIGSERIAL       PRIMARY KEY,
    level_name      VARCHAR(32)     NOT NULL,
    level_code      SMALLINT        NOT NULL UNIQUE,
    min_points      INTEGER         NOT NULL DEFAULT 0,
    discount_rate   DECIMAL(3,2)    DEFAULT 1.00 COMMENT '折扣率, 如0.95表示95折',
    benefits        VARCHAR(512)    DEFAULT '',
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
-- 初始化会员等级
INSERT INTO member_level (level_name, level_code, min_points, discount_rate, benefits) VALUES
('普通会员', 0, 0, 1.00, '基础购物体验'),
('银卡会员', 1, 1000, 0.98, '98折优惠'),
('金卡会员', 2, 5000, 0.95, '95折优惠 + 包邮'),
('钻石会员', 3, 20000, 0.90, '9折优惠 + 包邮 + 专属客服')
ON CONFLICT DO NOTHING;

-- 积分流水表
CREATE TABLE IF NOT EXISTS points_log (
    id              BIGSERIAL       PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    points          INTEGER         NOT NULL COMMENT '正数获取, 负数消费',
    type            SMALLINT        NOT NULL COMMENT '1-获取 2-消费',
    source          VARCHAR(32)     NOT NULL COMMENT '来源: ORDER/SIGN_IN/ACTIVITY',
    related_order_no VARCHAR(32)    DEFAULT '',
    description     VARCHAR(256)    DEFAULT '',
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_points_user ON points_log(user_id);

-- 签到记录表
CREATE TABLE IF NOT EXISTS sign_in (
    id              BIGSERIAL       PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    sign_date       DATE            NOT NULL,
    points_earned   INTEGER         NOT NULL DEFAULT 10,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX idx_sign_user_date ON sign_in(user_id, sign_date);

-- 收藏表
CREATE TABLE IF NOT EXISTS favorite (
    id              BIGSERIAL       PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    product_id      BIGINT          NOT NULL,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX idx_favorite_user_product ON favorite(user_id, product_id);

-- 优惠券模板表
CREATE TABLE IF NOT EXISTS coupon_template (
    id              BIGSERIAL       PRIMARY KEY,
    name            VARCHAR(64)     NOT NULL,
    type            SMALLINT        NOT NULL COMMENT '1-满减券 2-折扣券',
    threshold_amount DECIMAL(10,2)  NOT NULL DEFAULT 0.00 COMMENT '使用门槛金额',
    discount_value  DECIMAL(10,2)   NOT NULL DEFAULT 0.00 COMMENT '满减金额 或 折扣率',
    min_purchase    DECIMAL(10,2)   DEFAULT 0.00 COMMENT '最低消费',
    total_count     INTEGER         NOT NULL DEFAULT 0,
    remaining_count INTEGER         NOT NULL DEFAULT 0,
    start_time      TIMESTAMP       NOT NULL,
    end_time        TIMESTAMP       NOT NULL,
    status          SMALLINT        DEFAULT 1 COMMENT '0-下架 1-上架',
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);

-- 用户优惠券表
CREATE TABLE IF NOT EXISTS user_coupon (
    id              BIGSERIAL       PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    coupon_template_id BIGINT       NOT NULL,
    status          SMALLINT        NOT NULL DEFAULT 0 COMMENT '0-未使用 1-已使用 2-已过期',
    used_time       TIMESTAMP,
    order_id        BIGINT,
    expire_time     TIMESTAMP       NOT NULL,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_user_coupon_user ON user_coupon(user_id);
CREATE INDEX idx_user_coupon_status ON user_coupon(status);

-- ========================================
-- Phase 1-3 新增表
-- ========================================

-- 售后/退款表
CREATE TABLE IF NOT EXISTS t_refund (
    id              BIGSERIAL       PRIMARY KEY,
    refund_no       VARCHAR(32)     NOT NULL UNIQUE,
    user_id         BIGINT          NOT NULL,
    order_id        BIGINT          NOT NULL,
    order_item_id   BIGINT          NOT NULL,
    order_no        VARCHAR(32)     NOT NULL,
    type            SMALLINT        NOT NULL,
    status          SMALLINT        NOT NULL DEFAULT 0,
    reason          VARCHAR(128)    NOT NULL,
    description     VARCHAR(512)    DEFAULT '',
    images          TEXT            DEFAULT '[]',
    refund_amount   DECIMAL(10,2)   NOT NULL DEFAULT 0.00,
    return_tracking_no VARCHAR(64)  DEFAULT '',
    return_company  VARCHAR(64)     DEFAULT '',
    reject_reason   VARCHAR(256)    DEFAULT '',
    refund_time     TIMESTAMP,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    deleted         SMALLINT        DEFAULT 0
);
CREATE INDEX idx_refund_user ON t_refund(user_id);
CREATE INDEX idx_refund_order ON t_refund(order_id);
CREATE INDEX idx_refund_status ON t_refund(status);

-- 商品评价表
CREATE TABLE IF NOT EXISTS t_product_review (
    id              BIGSERIAL       PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    product_id      BIGINT          NOT NULL,
    order_id        BIGINT          NOT NULL,
    order_item_id   BIGINT          NOT NULL,
    nickname        VARCHAR(64)     DEFAULT '',
    avatar          VARCHAR(512)    DEFAULT '',
    rating          SMALLINT        NOT NULL,
    content         VARCHAR(512)    DEFAULT '',
    images          TEXT            DEFAULT '[]',
    sku_name        VARCHAR(128)    DEFAULT '',
    is_anonymous    BOOLEAN         DEFAULT FALSE,
    reply_status    SMALLINT        DEFAULT 0,
    reply_content   VARCHAR(512)    DEFAULT '',
    reply_time      TIMESTAMP,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_review_product ON t_product_review(product_id);
CREATE INDEX idx_review_user ON t_product_review(user_id);

-- Banner轮播图表
CREATE TABLE IF NOT EXISTS t_banner (
    id              BIGSERIAL       PRIMARY KEY,
    title           VARCHAR(64)     NOT NULL DEFAULT '',
    image_url       VARCHAR(512)    NOT NULL,
    link_type       VARCHAR(32)     DEFAULT 'product',
    link_value      VARCHAR(128)    DEFAULT '',
    sort_order      INTEGER         DEFAULT 0,
    status          SMALLINT        DEFAULT 1,
    position        SMALLINT        DEFAULT 1,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    deleted         SMALLINT        DEFAULT 0
);

-- 公告表
CREATE TABLE IF NOT EXISTS t_announcement (
    id              BIGSERIAL       PRIMARY KEY,
    title           VARCHAR(128)    NOT NULL,
    content         TEXT            DEFAULT '',
    status          SMALLINT        DEFAULT 1,
    publish_time    TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    deleted         SMALLINT        DEFAULT 0
);

-- 分享记录表
CREATE TABLE IF NOT EXISTS t_share_record (
    id              BIGSERIAL       PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    target_user_id  BIGINT          DEFAULT 0,
    product_id      BIGINT          NOT NULL,
    share_type      VARCHAR(32)     DEFAULT 'product',
    share_code      VARCHAR(32)     NOT NULL,
    click_count     INTEGER         DEFAULT 0,
    convert_count   INTEGER         DEFAULT 0,
    commission      DECIMAL(10,2)   DEFAULT 0.00,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_share_user ON t_share_record(user_id);
CREATE INDEX idx_share_code ON t_share_record(share_code);

-- 分销配置表
CREATE TABLE IF NOT EXISTS t_distribution_config (
    id              BIGSERIAL       PRIMARY KEY,
    level           SMALLINT        NOT NULL,
    commission_rate DECIMAL(5,4)    NOT NULL,
    status          SMALLINT        DEFAULT 1,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO t_distribution_config (level, commission_rate) VALUES (1, 0.1000), (2, 0.0500) ON CONFLICT DO NOTHING;

-- 用户行为表
CREATE TABLE IF NOT EXISTS t_user_behavior (
    id              BIGSERIAL       PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    product_id      BIGINT          NOT NULL,
    behavior_type   VARCHAR(32)     NOT NULL,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_behavior_user ON t_user_behavior(user_id);
CREATE INDEX idx_behavior_type ON t_user_behavior(behavior_type);

-- 管理员角色表 (RBAC)
CREATE TABLE IF NOT EXISTS t_admin_role (
    id              BIGSERIAL       PRIMARY KEY,
    role_code       VARCHAR(32)     NOT NULL UNIQUE,
    role_name       VARCHAR(64)     NOT NULL,
    permissions     TEXT            DEFAULT '[]',
    status          SMALLINT        DEFAULT 1,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    deleted         SMALLINT        DEFAULT 0
);
INSERT INTO t_admin_role (role_code, role_name, permissions) VALUES
('SUPER_ADMIN', '超级管理员', '["*"]'),
('OPERATOR', '运营', '["product:manage","order:manage","content:manage","stats:view"]'),
('CUSTOMER_SERVICE', '客服', '["order:view","refund:manage","review:view"]')
ON CONFLICT DO NOTHING;

-- 管理员表 (RBAC版本)
CREATE TABLE IF NOT EXISTS t_admin_user (
    id              BIGSERIAL       PRIMARY KEY,
    username        VARCHAR(64)     NOT NULL UNIQUE,
    password        VARCHAR(256)    NOT NULL,
    real_name       VARCHAR(64)     DEFAULT '',
    phone           VARCHAR(20)     DEFAULT '',
    role_code       VARCHAR(32)     NOT NULL DEFAULT 'OPERATOR',
    status          SMALLINT        DEFAULT 1,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    deleted         SMALLINT        DEFAULT 0
);
