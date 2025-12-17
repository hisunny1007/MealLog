package com.yunyun.meallog.meal.dto.response;

import com.yunyun.meallog.meal.domain.Meal;
import com.yunyun.meallog.meal.domain.MealType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealCalendarSummaryResponseDto {

    private LocalDate date;
    private Integer breakfastScore; // null 가능
    private Integer lunchScore;
    private Integer dinnerScore;

}
