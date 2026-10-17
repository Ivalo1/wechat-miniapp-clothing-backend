package com.ivalo1.wechatbackend.infrastructure.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.admin-expiration}")
    private long adminExpiration;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateUserToken(Long userId, String openid) {
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("openid", openid)
                .claim("type", "user")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    public String generateAdminToken(Long adminId, String username, String role) {
        return Jwts.builder()
                .subject(String.valueOf(adminId))
                .claim("username", username)
                .claim("type", "admin")
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + adminExpiration))
                .signWith(getKey())
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser().verifyWith(getKey()).build()
                .parseSignedClaims(token).getPayload();
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
