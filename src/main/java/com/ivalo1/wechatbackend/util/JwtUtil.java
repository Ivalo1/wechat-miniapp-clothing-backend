package com.ivalo1.wechatbackend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ivalo1.wechatbackend.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret:secret-key-please-change}")
    private String secret;

    // token valid for 7 days
    private static final long EXPIRE_MS = 7 * 24 * 60 * 60 * 1000L;

    public String generateToken(User user) {
        Algorithm alg = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(String.valueOf(user.getId()))
                .withClaim("openId", user.getOpenId())
                .withClaim("nickname", user.getNickname())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_MS))
                .sign(alg);
    }

    public DecodedJWT verify(String token) {
        Algorithm alg = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(alg).build();
        return verifier.verify(token);
    }
}
