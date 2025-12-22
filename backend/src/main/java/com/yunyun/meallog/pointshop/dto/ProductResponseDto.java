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
    private String weight;   
    private String protein;   
    private String calorie;   
}
