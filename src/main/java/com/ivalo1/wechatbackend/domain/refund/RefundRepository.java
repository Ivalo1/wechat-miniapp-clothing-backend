package com.ivalo1.wechatbackend.domain.refund;

public interface RefundRepository {
    Refund findById(Long id);
    Refund findByRefundNo(String refundNo);
    java.util.List<Refund> findByUserId(Long userId, Integer status);
    java.util.List<Refund> findByOrderId(Long orderId);
    void save(Refund refund);
    void update(Refund refund);
}
