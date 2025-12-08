package com.yunyun.meallog.meal.dto.response;

import com.yunyun.meallog.meal.domain.Meal;
import com.yunyun.meallog.meal.domain.MealType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 조회용
public class MealResponseDto {

    private Long id;
    private Long userId;

    private LocalDate date;
    private MealType mealType;

    private Long foodId;
    private String foodName;
    private int score;
    private String imageUrl;
    private String memo;

    private float calories;
    private float carbs;
    private float protein;
    private float fat;

    private LocalDateTime createdAt;

    public static MealResponseDto from(Meal meal) {
        return MealResponseDto.builder()
                .id(meal.getId())
                .userId(meal.getUserId())
                .date(meal.getDate())
                .mealType(meal.getMealType())
                .foodId(meal.getFoodId())
                .foodName(meal.getFoodName())
                .score(meal.getScore())
                .imageUrl(meal.getImageUrl())
                .memo(meal.getMemo())
                .calories(meal.getCalories())
                .carbs(meal.getCarbs())
                .protein(meal.getProtein())
                .fat(meal.getFat())
                .createdAt(meal.getCreatedAt())
                .build();
    }

}
