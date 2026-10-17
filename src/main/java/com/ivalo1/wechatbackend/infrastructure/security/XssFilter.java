package com.ivalo1.wechatbackend.infrastructure.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * XSS 过滤: 对所有请求参数进行 HTML 转义
 */
@Component
@Order(1)
public class XssFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
    }
}
