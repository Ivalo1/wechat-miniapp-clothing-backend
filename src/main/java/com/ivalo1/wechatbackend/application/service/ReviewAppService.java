package com.ivalo1.wechatbackend.application.service;
import com.ivalo1.wechatbackend.application.dto.ReviewDTO;
import com.ivalo1.wechatbackend.application.vo.ReviewVO;
import com.ivalo1.wechatbackend.common.BusinessException;
import com.ivalo1.wechatbackend.common.ErrorCode;
import com.ivalo1.wechatbackend.domain.member.User;
import com.ivalo1.wechatbackend.domain.member.MemberRepository;
import com.ivalo1.wechatbackend.domain.review.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class ReviewAppService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createReview(Long userId, Long productId, Long orderId, ReviewDTO dto) {
        User user = memberRepository.findById(userId);
        if (reviewRepository.hasReviewed(userId, dto.getOrderItemId()))
            throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "已评价过该商品");
        ProductReview review = ProductReview.create(userId, productId, orderId, dto.getOrderItemId(),
            user.getNickname(), user.getAvatar(), dto.getRating(), dto.getContent(), dto.getImages(), "");
        reviewRepository.save(review);
    }

    public List<ReviewVO> getProductReviews(Long productId, int page, int size) {
        return reviewRepository.findByProductId(productId, page, size).stream().map(r -> {
            ReviewVO vo = new ReviewVO();
            vo.setId(r.getId()); vo.setNickname(r.getIsAnonymous() ? "匿名用户" : r.getNickname());
            vo.setAvatar(r.getAvatar()); vo.setRating(r.getRating()); vo.setContent(r.getContent());
            vo.setImages(r.getImages()); vo.setSkuName(r.getSkuName()); vo.setCreateTime(r.getCreateTime());
            return vo;
        }).collect(Collectors.toList());
    }
}
