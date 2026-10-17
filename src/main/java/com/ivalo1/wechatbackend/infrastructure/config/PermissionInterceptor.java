package com.ivalo1.wechatbackend.infrastructure.config;
import com.ivalo1.wechatbackend.common.BusinessException;
import com.ivalo1.wechatbackend.common.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j @Component
public class PermissionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) return true;
        RequirePermission annotation = handlerMethod.getMethodAnnotation(RequirePermission.class);
        if (annotation == null) return true;
        String requiredPerm = annotation.value();
        // 从 request attribute 获取当前管理员权限列表
        String permissions = (String) request.getAttribute("adminPermissions");
        if (permissions == null || !permissions.contains(requiredPerm)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "无权限访问");
        }
        return true;
    }
}
