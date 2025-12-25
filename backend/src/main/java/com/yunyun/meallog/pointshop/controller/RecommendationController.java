package com.yunyun.meallog.pointshop.controller;

import com.yunyun.meallog.pointshop.dto.RecommendationResponseDto;
import com.yunyun.meallog.pointshop.service.RecommendationService;
import com.yunyun.meallog.global.jwt.JwtUtil; // 기존 JwtUtil 패키지 경로 확인 필요
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/pointshop")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final JwtUtil jwtUtil;

    @GetMapping("/recommendation")
    public ResponseEntity<RecommendationResponseDto> getRecommendation(
            @RequestHeader("Authorization") String authHeader) {

        // 1. Bearer 토큰 파싱
        String token = authHeader.substring(7);

        // 2. JwtUtil을 통해 userId 획득
        Long userId = jwtUtil.getUserId(token);

        // 3. 서비스 호출
        RecommendationResponseDto response = recommendationService.getPersonalizedRecommendation(userId);
        return ResponseEntity.ok(response);
    }
}