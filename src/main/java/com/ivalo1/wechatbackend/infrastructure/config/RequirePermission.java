package com.ivalo1.wechatbackend.infrastructure.config;
import java.lang.annotation.*;

@Target(ElementType.METHOD) @Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {
    String value(); // permission code
}
