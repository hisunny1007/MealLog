package com.yunyun.meallog.food.dto.request;

import com.yunyun.meallog.food.domain.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodRequestDto {

    private String name;
    private Float calories;
    private Float carbs;
    private Float protein;
    private Float fat;

    public Food toEntity(Long userId) {
        return Food.builder()
                .name(this.name)
                .calories(this.calories)
                .carbs(this.carbs)
                .protein(this.protein)
                .fat(this.fat)
                .isCustom(true)
                .createdBy(userId)
                .build();
    }

}
