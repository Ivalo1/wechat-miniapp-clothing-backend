package com.ivalo1.wechatbackend.infrastructure.lock;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IdempotentLock {
    String key();
    long expireMs() default 5000;
    String message() default "请勿重复提交";
}
