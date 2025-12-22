package com.yunyun.meallog.pointshop.service;

import com.yunyun.meallog.pointshop.dto.RecommendationResponseDto;

public interface RecommendationService {
    RecommendationResponseDto getPersonalizedRecommendation(Long userId);
}
