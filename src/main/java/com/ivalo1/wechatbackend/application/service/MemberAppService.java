package com.ivalo1.wechatbackend.application.service;
import com.ivalo1.wechatbackend.application.vo.MemberInfoVO;
import com.ivalo1.wechatbackend.common.*;
import com.ivalo1.wechatbackend.domain.member.*;
import com.ivalo1.wechatbackend.infrastructure.lock.RedisDistributedLock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate; import java.time.LocalDateTime; import java.util.UUID;

@Service @RequiredArgsConstructor
public class MemberAppService {
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final RedisDistributedLock distributedLock;

    public MemberInfoVO getMemberInfo(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        MemberInfoVO vo = new MemberInfoVO();
        vo.setNickname(user.getNickname()); vo.setAvatar(user.getAvatar());
        vo.setPoints(user.getPoints()); vo.setTotalConsumed(user.getTotalConsumed());
        vo.setSignedToday(user.hasSignedToday());
        if (user.getLevelId() != null) {
            MemberLevel level = memberRepository.findLevelById(user.getLevelId());
            if (level != null) vo.setLevelName(level.getName());
        }
        return vo;
    }

    @Transactional
    public void signIn(Long userId) {
        String lockKey = "lock:sign_in:" + userId;
        String lockValue = UUID.randomUUID().toString();
        if (!distributedLock.tryLock(lockKey, lockValue, 5000)) throw new BusinessException(ErrorCode.ALREADY_SIGNED_TODAY);
        try {
            User user = userRepository.findById(userId);
            if (user.hasSignedToday()) throw new BusinessException(ErrorCode.ALREADY_SIGNED_TODAY);
            user.addPoints(10);
            user.recordSignIn();
            userRepository.update(user);
            SignIn signIn = new SignIn();
            signIn.setUserId(userId); signIn.setSignDate(LocalDate.now());
            signIn.setPointsEarned(10); signIn.setCreateTime(LocalDateTime.now());
            memberRepository.saveSignIn(signIn);
            PointsLog log = new PointsLog();
            log.setUserId(userId); log.setType(PointsType.SIGN_IN.getCode());
            log.setPoints(10); log.setDescription("每日签到"); log.setCreateTime(LocalDateTime.now());
            memberRepository.savePointsLog(log);
        } finally { distributedLock.unlock(lockKey, lockValue); }
    }
}
