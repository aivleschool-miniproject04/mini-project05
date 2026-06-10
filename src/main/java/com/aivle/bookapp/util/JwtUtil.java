package com.aivle.bookapp.util;

import com.aivle.bookapp.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String createToken(User user) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(user.getUserId())
                .claim("nickname", user.getNickname())
                .claim("email", user.getEmail())
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
