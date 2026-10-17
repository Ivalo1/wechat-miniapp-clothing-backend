package com.ivalo1.wechatbackend.infrastructure.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * 请求日志过滤器: 记录所有请求的方法、URI、IP、耗时
 */
@Slf4j
@Component
@Order(3)
public class RequestLogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        long startTime = System.currentTimeMillis();

        String method = httpReq.getMethod();
        String uri = httpReq.getRequestURI();
        String ip = httpReq.getHeader("X-Real-IP");
        if (ip == null) ip = httpReq.getRemoteAddr();

        try {
            chain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            int status = ((jakarta.servlet.http.HttpServletResponse) response).getStatus();

            if (uri.startsWith("/api/actuator")) return; // 跳过健康检查日志

            if (duration > 1000) {
                log.warn("慢请求: {} {} IP={} status={} {}ms", method, uri, ip, status, duration);
            } else {
                log.debug("请求: {} {} IP={} status={} {}ms", method, uri, ip, status, duration);
            }
        }
    }
}
