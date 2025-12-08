package com.yunyun.meallog.meal.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meal {

    private Long id;
    private Long userId;

    private LocalDate date;
    // 식단 종류 선택 (enum 생성)
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
}
