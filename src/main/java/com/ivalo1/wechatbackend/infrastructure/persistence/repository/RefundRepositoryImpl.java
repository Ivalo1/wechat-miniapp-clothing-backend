package com.ivalo1.wechatbackend.infrastructure.persistence.repository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivalo1.wechatbackend.domain.refund.*;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.RefundMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository @RequiredArgsConstructor
public class RefundRepositoryImpl implements RefundRepository {
    private final RefundMapper refundMapper;
    @Override public Refund findById(Long id) { return refundMapper.selectById(id); }
    @Override public Refund findByRefundNo(String refundNo) { return refundMapper.selectOne(new LambdaQueryWrapper<Refund>().eq(Refund::getRefundNo, refundNo)); }
    @Override public List<Refund> findByUserId(Long userId, Integer status) { LambdaQueryWrapper<Refund> w = new LambdaQueryWrapper<>(); if (userId != null) w.eq(Refund::getUserId, userId); if (status != null) w.eq(Refund::getStatus, status); return refundMapper.selectList(w); }
    @Override public List<Refund> findByOrderId(Long orderId) { return refundMapper.selectList(new LambdaQueryWrapper<Refund>().eq(Refund::getOrderId, orderId)); }
    @Override public void save(Refund refund) { refundMapper.insert(refund); }
    @Override public void update(Refund refund) { refundMapper.updateById(refund); }
}
