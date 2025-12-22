package com.yunyun.meallog.pointshop.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private int pricePoint;
    private String imageUrl;
    private String category;

    // 피그마 시안 반영을 위한 추가 필드 (DB 컬럼에 맞춰 조정 필요)
    private String weight;    // 예: "150g"
    private String protein;   // 예: "16g"
    private String calorie;   // 예: "180 kcal"
}
