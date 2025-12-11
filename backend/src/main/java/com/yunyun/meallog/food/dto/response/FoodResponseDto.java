package com.yunyun.meallog.food.dto.response;

import com.yunyun.meallog.food.domain.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodResponseDto {
    
    private Long id;
    private String name;

    private Float calories;
    private Float carbs;
    private Float protein;
    private Float fat;
    private Boolean isCustom;

    public static FoodResponseDto from(Food food) {
        return FoodResponseDto.builder()
                .id(food.getId())
                .name(food.getName())
                .calories(food.getCalories())
                .carbs(food.getCarbs())
                .protein(food.getProtein())
                .fat(food.getFat())
                .isCustom(food.isCustom())
                .build();
    }
}

