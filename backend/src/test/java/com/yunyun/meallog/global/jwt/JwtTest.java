package com.yunyun.meallog.global.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("JWT 토큰 생성, 검증, 파싱 통합 테스트")
    void jwtIntegrationTest() {
        // Given: 테스트를 위한 사용자 ID 설정
        String userId = "123";

        // When: 사용자 ID를 기반으로 JWT 토큰 생성
        String token = jwtUtil.generateToken(userId);
        System.out.println("생성된 토큰: " + token);

        // Then: 생성된 토큰이 null이 아닌지 확인
        assertNotNull(token, "토큰은 null이 아니어야 합니다.");

        // When: 생성된 토큰의 유효성 검증
        boolean isTokenValid = jwtUtil.isTokenValid(token);

        // Then: 토큰이 유효한지 확인
        assertTrue(isTokenValid, "생성된 토큰은 유효해야 합니다.");

        // When: 토큰에서 사용자 ID 추출
        Long extractedUserId = jwtUtil.getUserId(token);

        // Then: 추출된 사용자 ID가 원래 ID와 일치하는지 확인
        assertEquals(Long.valueOf(userId), extractedUserId, "토큰에서 추출한 사용자 ID는 원래 ID와 일치해야 합니다.");
    }
}
