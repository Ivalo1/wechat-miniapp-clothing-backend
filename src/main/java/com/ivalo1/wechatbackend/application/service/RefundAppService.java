package com.ivalo1.wechatbackend.application.service;
import com.ivalo1.wechatbackend.application.dto.RefundApplyDTO;
import com.ivalo1.wechatbackend.application.vo.RefundVO;
import com.ivalo1.wechatbackend.common.BusinessException;
import com.ivalo1.wechatbackend.common.ErrorCode;
import com.ivalo1.wechatbackend.domain.order.Order;
import com.ivalo1.wechatbackend.domain.order.OrderItem;
import com.ivalo1.wechatbackend.domain.order.OrderRepository;
import com.ivalo1.wechatbackend.domain.refund.*;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.OrderItemMapper;
import com.ivalo1.wechatbackend.infrastructure.risk.RiskControlService;
import com.ivalo1.wechatbackend.infrastructure.util.OrderNoGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class RefundAppService {
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final RiskControlService riskControlService;
    private final OrderNoGenerator orderNoGenerator;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    public RefundVO applyRefund(Long userId, RefundApplyDTO dto) {
        // 风控检查
        var risk = riskControlService.checkRefundRisk(userId);
        if (!risk.isPassed()) throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, risk.getReason());

        // 查找订单项
        OrderItem item = orderItemMapper.selectById(dto.getOrderItemId());
        if (item == null) throw new BusinessException(ErrorCode.ORDER_NOT_FOUND, "订单项不存在");

        // 查找订单
        Order order = orderRepository.findByOrderNo(String.valueOf(item.getOrderId()));
        if (order == null) throw new BusinessException(ErrorCode.ORDER_NOT_FOUND, "订单不存在");

        Refund refund = Refund.apply(userId, order.getId(), dto.getOrderItemId(), order.getOrderNo(),
            dto.getType(), dto.getReason(), dto.getDescription(), dto.getImages(), item.getSubtotal());
        refund.setRefundNo(orderNoGenerator.generateRefundNo());
        refundRepository.save(refund);
        return toVO(refund);
    }

    @Transactional
    public void approveRefund(Long refundId) {
        Refund refund = findRefundById(refundId);
        refund.approve();
        refundRepository.update(refund);
    }

    @Transactional
    public void rejectRefund(Long refundId, String reason) {
        Refund refund = findRefundById(refundId);
        refund.reject(reason);
        refundRepository.update(refund);
    }

    @Transactional
    public void completeRefund(Long refundId) {
        Refund refund = findRefundById(refundId);
        refund.completeRefund();
        refundRepository.update(refund);
    }

    public List<RefundVO> getUserRefunds(Long userId, Integer status) {
        return refundRepository.findByUserId(userId, status).stream().map(this::toVO).collect(Collectors.toList());
    }

    private Refund findRefundById(Long refundId) {
        Refund refund = refundRepository.findById(refundId);
        if (refund == null) throw new BusinessException(ErrorCode.ORDER_NOT_FOUND, "售后单不存在");
        return refund;
    }

    private RefundVO toVO(Refund r) {
        RefundVO vo = new RefundVO();
        vo.setId(r.getId()); vo.setRefundNo(r.getRefundNo()); vo.setOrderNo(r.getOrderNo());
        vo.setType(r.getType()); vo.setStatus(r.getStatus()); vo.setReason(r.getReason());
        vo.setRefundAmount(r.getRefundAmount()); vo.setRejectReason(r.getRejectReason());
        vo.setCreateTime(r.getCreateTime()); vo.setRefundTime(r.getRefundTime());
        return vo;
    }
}
