package com.ivalo1.wechatbackend.infrastructure.persistence.repository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivalo1.wechatbackend.domain.recommend.*;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.UserBehaviorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository @RequiredArgsConstructor
public class RecommendRepositoryImpl implements RecommendRepository {
    private final UserBehaviorMapper behaviorMapper;
    @Override public void saveBehavior(UserBehavior behavior) { behaviorMapper.insert(behavior); }
    @Override public List<Long> findFavoriteCategoryIds(Long userId) { return List.of(); /* TODO: 从用户收藏/购买记录中提取偏好分类 */ }
    @Override public List<Long> findRecentViewedProductIds(Long userId, int limit) { return behaviorMapper.selectList(new LambdaQueryWrapper<UserBehavior>().eq(UserBehavior::getUserId, userId).eq(UserBehavior::getBehaviorType, "view").orderByDesc(UserBehavior::getCreateTime).last("LIMIT " + limit)).stream().map(UserBehavior::getProductId).collect(Collectors.toList()); }
}
