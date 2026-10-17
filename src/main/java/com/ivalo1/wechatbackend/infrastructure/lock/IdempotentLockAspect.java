package com.ivalo1.wechatbackend.infrastructure.lock;

import com.ivalo1.wechatbackend.common.BusinessException;
import com.ivalo1.wechatbackend.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.UUID;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class IdempotentLockAspect {

    private final RedisDistributedLock distributedLock;
    private final SpelExpressionParser parser = new SpelExpressionParser();
    private final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Around("@annotation(idempotentLock)")
    public Object around(ProceedingJoinPoint joinPoint, IdempotentLock idempotentLock) throws Throwable {
        String lockKey = resolveKey(joinPoint, idempotentLock.key());
        String lockValue = UUID.randomUUID().toString();
        if (!distributedLock.tryLock(lockKey, lockValue, idempotentLock.expireMs())) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, idempotentLock.message());
        }
        try {
            return joinPoint.proceed();
        } finally {
            distributedLock.unlock(lockKey, lockValue);
        }
    }

    private String resolveKey(ProceedingJoinPoint joinPoint, String keyExpression) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        StandardEvaluationContext context = new StandardEvaluationContext();
        String[] paramNames = nameDiscoverer.getParameterNames(method);
        Object[] args = joinPoint.getArgs();
        if (paramNames != null) {
            for (int i = 0; i < paramNames.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
        }
        return "idempotent:" + parser.parseExpression(keyExpression).getValue(context, String.class);
    }
}
