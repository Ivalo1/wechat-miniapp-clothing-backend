package com.ivalo1.wechatbackend.infrastructure.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisDistributedLock {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String LOCK_SCRIPT =
            "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then " +
            "    redis.call('pexpire', KEYS[1], ARGV[2]) return 1 else return 0 end";

    private static final String UNLOCK_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "    return redis.call('del', KEYS[1]) else return 0 end";

    private static final String RENEW_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "    return redis.call('pexpire', KEYS[1], ARGV[2]) else return 0 end";

    public boolean tryLock(String lockKey, String lockValue, long expireMs) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(LOCK_SCRIPT, Long.class);
        Long result = redisTemplate.execute(script, Collections.singletonList(lockKey), lockValue, expireMs);
        return result != null && result == 1L;
    }

    public boolean lock(String lockKey, String lockValue, long expireMs, long waitMs) {
        long deadline = System.currentTimeMillis() + waitMs;
        while (System.currentTimeMillis() < deadline) {
            if (tryLock(lockKey, lockValue, expireMs)) return true;
            try { Thread.sleep(50 + (long)(Math.random() * 50)); } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); return false;
            }
        }
        return false;
    }

    public boolean unlock(String lockKey, String lockValue) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(UNLOCK_SCRIPT, Long.class);
        Long result = redisTemplate.execute(script, Collections.singletonList(lockKey), lockValue);
        return result != null && result == 1L;
    }

    public boolean renewLock(String lockKey, String lockValue, long expireMs) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(RENEW_SCRIPT, Long.class);
        Long result = redisTemplate.execute(script, Collections.singletonList(lockKey), lockValue, expireMs);
        return result != null && result == 1L;
    }
}
