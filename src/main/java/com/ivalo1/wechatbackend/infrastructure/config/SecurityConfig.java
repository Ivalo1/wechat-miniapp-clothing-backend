package com.ivalo1.wechatbackend.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
            .cors(c -> c.configurationSource(corsConfigurationSource()))
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(h -> h
                .frameOptions(f -> f.deny())
                .contentTypeOptions(c -> {})
                .xssProtection(x -> {})
            )
            .authorizeHttpRequests(a -> a
                // 公开接口 - 认证
                .requestMatchers("/mini/auth/**").permitAll()
                .requestMatchers("/admin/auth/**").permitAll()
                // 公开接口 - 商品浏览
                .requestMatchers("/mini/product/**", "/mini/category/**").permitAll()
                .requestMatchers("/mini/content/**").permitAll()
                .requestMatchers("/mini/review/product/**").permitAll()
                // 公开接口 - 支付回调
                .requestMatchers("/mini/pay/notify").permitAll()
                // 公开接口 - 分享
                .requestMatchers("/mini/share/click").permitAll()
                // 公开接口 - 文档 & 健康检查
                .requestMatchers("/doc.html", "/webjars/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                // 管理端需要 ADMIN 角色
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // 小程序端需要 USER 角色
                .requestMatchers("/mini/**").hasRole("USER")
                .anyRequest().permitAll()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("https://*.weixin.qq.com", "https://*.servicewechat.com", "http://localhost:*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
