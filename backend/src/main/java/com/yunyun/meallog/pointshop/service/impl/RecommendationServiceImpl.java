package com.yunyun.meallog.pointshop.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunyun.meallog.global.common.GptClient;
import com.yunyun.meallog.meal.service.MealService;
import com.yunyun.meallog.pointshop.dao.ProductDao;
import com.yunyun.meallog.pointshop.domain.Product;
import com.yunyun.meallog.pointshop.dto.ProductResponseDto;
import com.yunyun.meallog.pointshop.dto.RecommendationResponseDto;
import com.yunyun.meallog.pointshop.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {
    private final MealService mealService;
    private final ProductDao productDao;
    private final GptClient gptClient;
    private final ObjectMapper objectMapper;

    @Override
    public RecommendationResponseDto getPersonalizedRecommendation(Long userId) {
        log.info("AI 추천 요청 시작 - 사용자 ID: {}", userId);
        List<ProductResponseDto> recommendedProducts = new ArrayList<>();
        List<String> meals = mealService.getFoodNamesLastSevenDays(userId);
        log.info("지난 7일간의 식단 기록 조회: {}개의 기록 발견", meals.size());
        log.debug("조회된 식단 목록: {}", meals);

        if (meals == null || meals.isEmpty()) {
            log.warn("사용자 ID {}에 대한 식단 기록이 없어 랜덤 상품을 추천합니다.", userId);
            productDao.selectRandomProducts(3).stream()
                    .map(this::convertToDto)
                    .forEach(recommendedProducts::add);
            log.info("랜덤 추천 상품: {}", recommendedProducts.stream().map(ProductResponseDto::getName).collect(Collectors.toList()));
            return new RecommendationResponseDto(recommendedProducts);
        }

        try {
            String prompt = "식단: " + meals.toString() + ". 분류: [단백질, 야채, 도시락]. 각 카테고리 빈도를 JSON {카테고리:횟수}로만 응답해.";
            log.info("GPT 프롬프트 생성: {}", prompt);

            String jsonResult = gptClient.analyzeMealPattern(prompt);
            log.info("GPT 응답 수신: {}", jsonResult);

            if (jsonResult == null || jsonResult.contains("\"error\"") || jsonResult.isEmpty()) {
                log.error("GPT 분석 실패 응답 수신, 폴백 로직으로 진입합니다.");
                throw new RuntimeException("GPT API response invalid"); // catch 블록으로 던져서 랜덤 추천 실행
            }

            Map<String, Integer> categoryCounts = objectMapper.readValue(jsonResult, new TypeReference<Map<String, Integer>>() {});

            categoryCounts.entrySet().stream()
                    .filter(entry -> entry.getValue() >= 1)
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                    .limit(3)
                    .forEach(entry -> {
                        log.debug("추천된 카테고리 '{}'에 대한 상품 조회", entry.getKey());
                        Product p = productDao.selectOneRandomByCategory(entry.getKey());
                        if (p != null) {
                            recommendedProducts.add(convertToDto(p));
                            log.debug("카테고리 '{}'에 상품 '{}' 추천", entry.getKey(), p.getName());
                        } else {
                            log.warn("카테고리 '{}'에 추천할 상품이 없습니다.", entry.getKey());
                        }
                    });

            if (recommendedProducts.size() < 3) {
                int needed = 3 - recommendedProducts.size();
                log.info("추천 상품이 3개 미만이어서 {}개의 랜덤 상품을 추가합니다.", needed);
                productDao.selectRandomProducts(needed).stream()
                        .map(this::convertToDto)
                        .forEach(recommendedProducts::add);
            }

        } catch (Exception e) {
            log.error("AI 추천 처리 중 예외 발생. 사용자 ID: {}", userId, e);
            recommendedProducts.clear();
            productDao.selectRandomProducts(3).stream()
                    .map(this::convertToDto)
                    .forEach(recommendedProducts::add);
            log.warn("폴백(Fallback) 실행: 3개의 랜덤 상품을 추천합니다.");
        }

        log.info("최종 추천 상품 목록: {}", recommendedProducts.stream().map(ProductResponseDto::getName).collect(Collectors.toList()));
        return new RecommendationResponseDto(recommendedProducts);
    }

    private ProductResponseDto convertToDto(Product p) {
        return ProductResponseDto.builder()
                .id(p.getId()).name(p.getName()).description(p.getDescription())
                .pricePoint(p.getPricePoint()).imageUrl(p.getImageUrl()).category(p.getCategory())
                .build();
    }
}
