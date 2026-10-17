package com.ivalo1.wechatbackend.infrastructure.util;

import cn.hutool.core.lang.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class OrderNoGenerator {

    private final Snowflake snowflake;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public String generate() {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        long snowflakeId = snowflake.nextId();
        String suffix = String.valueOf(snowflakeId);
        if (suffix.length() > 6) suffix = suffix.substring(suffix.length() - 6);
        int random = ThreadLocalRandom.current().nextInt(1000, 9999);
        return "CL" + timestamp + suffix + random;
    }

    public String generateRefundNo() {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        long snowflakeId = snowflake.nextId();
        String suffix = String.valueOf(snowflakeId);
        if (suffix.length() > 6) suffix = suffix.substring(suffix.length() - 6);
        int random = ThreadLocalRandom.current().nextInt(1000, 9999);
        return "RF" + timestamp + suffix + random;
    }
}
