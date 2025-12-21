package com.yunyun.meallog.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
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

    // 서버 시작 시 SecretKey 초기화
    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(
                key.getBytes(StandardCharsets.UTF_8)
        );
    }

    /**
     *  JWT 토큰 생성
     * - userId는 Long으로 통일
     * - subject 대신 claim에 명시적으로 userId 저장
     */
    public String generateToken(Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .claim("userId", userId) 
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS384)
                .compact();
    }

    /**
     *  JWT 토큰에서 userId 추출
     * - 기존 Integer 토큰과의 호환을 위해 타입 분기 처리
     * - 최종 반환 타입은 Long
     */
    public Long getUserId(String token) {
        Claims claims = getClaims(token);

        Object userId = claims.get("userId");

        if (userId instanceof Integer) {
            return ((Integer) userId).longValue();
        }

        return (Long) userId;
    }

    /**
     * JWT 유효성 검증
     * - 서명 검증
     * - 만료 시간 검증
     */
    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            Date expiration = claims.getExpiration();

            return expiration != null && !expiration.before(new Date());

        } catch (io.jsonwebtoken.security.SignatureException e) {
            System.err.println("유효하지 않은 JWT 서명");
            return false;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            System.err.println("JWT 만료");
            return false;
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            System.err.println("잘못된 JWT 형식");
            return false;
        } catch (Exception e) {
            System.err.println("JWT 검증 중 오류 발생: " + e.getMessage());
            return false;
        }
    }

    /**
     * 토큰에서 Claims 추출 (내부 공용 메서드)
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
