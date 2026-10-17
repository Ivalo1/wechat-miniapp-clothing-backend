package com.ivalo1.wechatbackend.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivalo1.wechatbackend.domain.member.*;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.MemberLevelMapper;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.PointsLogMapper;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.SignInMapper;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final SignInMapper signInMapper;
    private final PointsLogMapper pointsLogMapper;
    private final MemberLevelMapper memberLevelMapper;
    private final UserMapper userMapper;

    @Override
    public User findById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public SignIn findTodaySignIn(Long userId) {
        return signInMapper.selectOne(new LambdaQueryWrapper<SignIn>()
                .eq(SignIn::getUserId, userId).eq(SignIn::getSignDate, LocalDate.now()));
    }

    @Override
    public void saveSignIn(SignIn signIn) {
        signInMapper.insert(signIn);
    }

    @Override
    public void savePointsLog(PointsLog log) {
        pointsLogMapper.insert(log);
    }

    @Override
    public List<MemberLevel> findAllLevels() {
        return memberLevelMapper.selectList(new LambdaQueryWrapper<MemberLevel>().orderByAsc(MemberLevel::getLevel));
    }

    @Override
    public MemberLevel findLevelById(Long id) {
        return memberLevelMapper.selectById(id);
    }

    @Override
    public MemberLevel findLevelByConsumed(BigDecimal totalConsumed) {
        return memberLevelMapper.selectOne(new LambdaQueryWrapper<MemberLevel>()
                .le(MemberLevel::getMinConsumed, totalConsumed)
                .orderByDesc(MemberLevel::getMinConsumed)
                .last("LIMIT 1"));
    }
}
