package com.yunyun.meallog.meal.dto.request;

import com.yunyun.meallog.meal.domain.Meal;
import com.yunyun.meallog.meal.domain.MealType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 등록 수정용
public class MealRequestDto {

    @NotNull(message = "날짜는 필수입니다")
    private LocalDate date;

    @NotNull(message = "식단 종류는 필수입니다")
    private MealType mealType;

    // 음식 선택 시
    private Long foodId;

    // 음식 직접 추가 시
    private String foodName;

    @NotNull(message = "식단 점수 선택은 필수입니다.")
    @Min(1)
    @Max(5)
    private int score;

    private String memo;
    private String imageUrl;

    private float calories;
    private float carbs;
    private float protein;
    private float fat;

    public Meal toEntity(Long userId) {
        return Meal.builder()
                .userId(userId)
                .date(this.date)
                .mealType(this.mealType)
                .foodId(this.foodId)
                .foodName(this.foodName)
                .score(this.score)
                .imageUrl(this.imageUrl)
                .memo(this.memo)
                .calories(this.calories)
                .carbs(this.carbs)
                .protein(this.protein)
                .fat(this.fat)
                .build();
    }
}
