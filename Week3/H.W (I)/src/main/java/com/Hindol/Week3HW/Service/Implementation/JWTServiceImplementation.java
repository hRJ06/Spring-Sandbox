package com.Hindol.Week3HW.Service.Implementation;

import com.Hindol.Week3HW.Entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JWTServiceImplementation {
    @Value("${jwt.secret_key}")
    private String jwt_secret_key;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwt_secret_key.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserEntity userEntity) {
        return Jwts.builder()
                .subject(userEntity.getId().toString())
                .claim("email", userEntity.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .signWith(getSecretKey())
                .compact();
    }
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts
                .parser().
                verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.valueOf(claims.getSubject());
    }
}
