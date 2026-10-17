package com.ivalo1.wechatbackend.infrastructure.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 接口限流过滤器
 * 基于 Redis 滑动窗口，按 IP 限制每分钟请求数
 */
@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class RateLimitFilter implements Filter {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final int MAX_REQUESTS_PER_MINUTE = 120;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        String clientIp = getClientIp(httpReq);
        String key = "rate_limit:" + clientIp;

        Long count = redisTemplate.opsForValue().increment(key);
        if (count != null && count == 1) {
            redisTemplate.expire(key, 60, TimeUnit.SECONDS);
        }

        if (count != null && count > MAX_REQUESTS_PER_MINUTE) {
            log.warn("接口限流: IP={}, count={}", clientIp, count);
            httpResp.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResp.setContentType("application/json;charset=UTF-8");
            httpResp.getWriter().write("{\"code\":429,\"message\":\"请求过于频繁，请稍后再试\"}");
            return;
        }

        chain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多级代理取第一个
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
