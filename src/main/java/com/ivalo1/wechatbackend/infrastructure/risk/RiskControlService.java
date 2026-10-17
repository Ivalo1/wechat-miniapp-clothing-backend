package com.ivalo1.wechatbackend.infrastructure.risk;
import com.ivalo1.wechatbackend.domain.risk.RiskCheckResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Slf4j @Service @RequiredArgsConstructor
public class RiskControlService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RiskCheckResult checkOrderRisk(Long userId) {
        String freqKey = "risk:order_freq:" + userId;
        Long count = redisTemplate.opsForValue().increment(freqKey);
        if (count == 1) redisTemplate.expire(freqKey, 60, TimeUnit.SECONDS);
        if (count > 5) return RiskCheckResult.reject("下单频率过高", 2);
        String dailyKey = "risk:order_daily:" + userId;
        Long dailyCount = redisTemplate.opsForValue().increment(dailyKey);
        if (dailyCount == 1) redisTemplate.expire(dailyKey, 86400, TimeUnit.SECONDS);
        if (dailyCount > 20) return RiskCheckResult.reject("今日下单次数已达上限", 1);
        return RiskCheckResult.pass();
    }

    public RiskCheckResult checkRefundRisk(Long userId) {
        String key = "risk:refund_count:" + userId;
        Long count = redisTemplate.opsForValue().increment(key);
        if (count == 1) redisTemplate.expire(key, 86400, TimeUnit.SECONDS);
        if (count > 5) return RiskCheckResult.reject("今日退款次数过多", 2);
        return RiskCheckResult.pass();
    }
}
