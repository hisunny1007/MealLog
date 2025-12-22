package com.yunyun.meallog.pointshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductViewDto {
    private Long id;
    private String name;
    private String description;
    private int pricePoint;
    private String imageUrl;
    private String category;
}
