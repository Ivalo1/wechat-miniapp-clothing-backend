package com.ivalo1.wechatbackend.infrastructure.config;

import com.ivalo1.wechatbackend.infrastructure.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws java.io.IOException, jakarta.servlet.ServletException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (jwtUtil.validateToken(token)) {
                Claims claims = jwtUtil.parseToken(token);
                Long userId = Long.parseLong(claims.getSubject());
                String type = claims.get("type", String.class);
                List<SimpleGrantedAuthority> authorities;
                if ("admin".equals(type)) {
                    String role = claims.get("role", String.class);
                    authorities = List.of(new SimpleGrantedAuthority("ROLE_" + (role != null ? role : "ADMIN")));
                } else {
                    authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
                }
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(userId, null, authorities));
            }
        }
        chain.doFilter(request, response);
    }
}
