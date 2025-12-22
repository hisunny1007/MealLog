package com.yunyun.meallog.meal.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealCreateResponseDto {
    private MealResponseDto meal;      // 등록된 식단 정보
    private int currentTotalPoint;     // 업데이트된 유저의 총 포인트
}