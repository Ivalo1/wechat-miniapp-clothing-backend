package com.ivalo1.wechatbackend.infrastructure.config;

import cn.hutool.core.lang.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SnowflakeConfig {

    @Value("${snowflake.worker-id:0}")
    private long configWorkerId;

    @Value("${snowflake.datacenter-id:1}")
    private long datacenterId;

    private final RedisTemplate<String, Object> redisTemplate;

    public SnowflakeConfig(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public Snowflake snowflake() {
        long workerId = allocateWorkerId();
        log.info("Snowflake initialized: workerId={}, datacenterId={}", workerId, datacenterId);
        return new Snowflake(workerId, datacenterId);
    }

    private long allocateWorkerId() {
        try {
            String key = "snowflake:worker_id_counter";
            Long counter = redisTemplate.opsForValue().increment(key);
            if (counter != null) {
                long workerId = counter % 32;
                if (counter % 100 == 0) redisTemplate.expire(key, java.time.Duration.ofDays(1));
                return workerId;
            }
        } catch (Exception e) {
            log.warn("Redis allocate workerId failed, use config value", e);
        }
        return configWorkerId;
    }
}
