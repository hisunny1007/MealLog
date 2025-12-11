package com.yunyun.meallog.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.expiration_ms}")
    private Long expirationMs;
    private SecretKey secretKey;

    @PostConstruct
    public void init(){
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }
    //사용자 ID를 기반으로 jwt 토큰 생성
    public String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(userId)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }
    //jwt 토큰을 파싱하여 사용자 id를 추출
    public String getUserId(String token) {
        return getClaims(token).getSubject();

    }

    //jwt 토큰 유효성 검증
    public boolean isTokenValid(String token) {
        try{
            Claims claims = getClaims(token);
            Date expiration = claims.getExpiration();
            return expiration != null && !expiration.before(new Date());
        } catch (Exception e) {
            //토큰 파싱 중 예외 발생 시 유효하지 않은 토큰으로 간주
            return false;
        }
    }
    //토큰에서 클레임 정보를 추출
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
