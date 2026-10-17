package com.ivalo1.wechatbackend.domain.member;

import java.math.BigDecimal;

public interface MemberRepository {
    User findById(Long id);
    SignIn findTodaySignIn(Long userId);
    void saveSignIn(SignIn signIn);
    void savePointsLog(PointsLog log);
    java.util.List<MemberLevel> findAllLevels();
    MemberLevel findLevelById(Long id);
    MemberLevel findLevelByConsumed(BigDecimal totalConsumed);
}
