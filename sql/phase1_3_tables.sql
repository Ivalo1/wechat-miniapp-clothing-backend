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
    type            SMALLINT        NOT NULL COMMENT '1仅退款 2退货退款 3换货',
    status          SMALLINT        NOT NULL DEFAULT 0 COMMENT '0待审核 1已同意 2已拒绝 3已退货 4已退款 5已取消',
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
    rating          SMALLINT        NOT NULL COMMENT '1-5星',
    content         VARCHAR(512)    DEFAULT '',
    images          TEXT            DEFAULT '[]',
    sku_name        VARCHAR(128)    DEFAULT '',
    is_anonymous    BOOLEAN         DEFAULT FALSE,
    reply_status    SMALLINT        DEFAULT 0 COMMENT '0未回复 1已回复',
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
    link_type       VARCHAR(32)     DEFAULT 'product' COMMENT 'product/category/url',
    link_value      VARCHAR(128)    DEFAULT '',
    sort_order      INTEGER         DEFAULT 0,
    status          SMALLINT        DEFAULT 1 COMMENT '0隐藏 1显示',
    position        SMALLINT        DEFAULT 1 COMMENT '1首页 2分类页',
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    deleted         SMALLINT        DEFAULT 0
);

-- 公告表
CREATE TABLE IF NOT EXISTS t_announcement (
    id              BIGSERIAL       PRIMARY KEY,
    title           VARCHAR(128)    NOT NULL,
    content         TEXT            DEFAULT '',
    status          SMALLINT        DEFAULT 1 COMMENT '0隐藏 1显示',
    publish_time    TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    deleted         SMALLINT        DEFAULT 0
);

-- 分享记录表
CREATE TABLE IF NOT EXISTS t_share_record (
    id              BIGSERIAL       PRIMARY KEY,
    user_id         BIGINT          NOT NULL COMMENT '分享者',
    target_user_id  BIGINT          DEFAULT 0 COMMENT '被分享者',
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
    level           SMALLINT        NOT NULL COMMENT '1一级 2二级',
    commission_rate DECIMAL(5,4)    NOT NULL COMMENT '佣金比例',
    status          SMALLINT        DEFAULT 1,
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    update_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO t_distribution_config (level, commission_rate) VALUES (1, 0.1000), (2, 0.0500) ON CONFLICT DO NOTHING;

-- 用户行为表 (推荐系统)
CREATE TABLE IF NOT EXISTS t_user_behavior (
    id              BIGSERIAL       PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    product_id      BIGINT          NOT NULL,
    behavior_type   VARCHAR(32)     NOT NULL COMMENT 'view/cart/favorite/purchase',
    create_time     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_behavior_user ON t_user_behavior(user_id);
CREATE INDEX idx_behavior_type ON t_user_behavior(behavior_type);

-- 管理员角色表 (RBAC)
CREATE TABLE IF NOT EXISTS t_admin_role (
    id              BIGSERIAL       PRIMARY KEY,
    role_code       VARCHAR(32)     NOT NULL UNIQUE,
    role_name       VARCHAR(64)     NOT NULL,
    permissions     TEXT            DEFAULT '[]' COMMENT 'JSON权限码数组',
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
