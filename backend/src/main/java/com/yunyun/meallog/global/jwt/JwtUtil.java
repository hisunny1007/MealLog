package com.yunyun.meallog.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
                .claim("id", userId) // [수정]
//                .subject(userId)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS384) // [수정]
                .compact();
    }
    //jwt 토큰을 파싱하여 사용자 id를 추출
//    public String getUserId(String token) {
//        return getClaims(token).getSubject();
//
//    }

    // [수정]
    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return Long.parseLong(claims.get("id").toString()); // [수정]
    }


    //jwt 토큰 유효성 검증
    public boolean isTokenValid(String token) {
        try{
            Claims claims = getClaims(token);
            Date expiration = claims.getExpiration();
            boolean isValid = expiration != null && !expiration.before(new Date());
            if (isValid) {
                System.out.println("JWT 인증 성공!");
            }
            return isValid;
        } catch (io.jsonwebtoken.security.SignatureException e) {
            System.err.println("유효하지 않은 JWT: " + e.getMessage());
            return false;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            System.err.println("JWT 만료: " + e.getMessage());
            return false;
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            System.err.println("잘못된 JWT: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("JWT 인증 에러: " + e.getClass().getName() + " - " + e.getMessage());
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
