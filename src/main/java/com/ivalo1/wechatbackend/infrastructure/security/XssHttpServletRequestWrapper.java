package com.ivalo1.wechatbackend.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.web.util.HtmlUtils;

/**
 * XSS 请求包装器: 对参数值进行 HTML 转义
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return value != null ? HtmlUtils.htmlEscape(value) : null;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) return null;
        String[] escaped = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            escaped[i] = values[i] != null ? HtmlUtils.htmlEscape(values[i]) : null;
        }
        return escaped;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return value != null ? HtmlUtils.htmlEscape(value) : null;
    }
}
