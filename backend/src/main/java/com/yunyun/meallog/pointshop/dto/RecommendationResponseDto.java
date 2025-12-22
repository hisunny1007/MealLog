package com.yunyun.meallog.pointshop.dto;


import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RecommendationResponseDto {

    private List<ProductResponseDto> recommendedProducts; // 추천 상품 리스트
}
