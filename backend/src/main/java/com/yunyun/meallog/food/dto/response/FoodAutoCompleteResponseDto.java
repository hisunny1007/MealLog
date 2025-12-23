package com.yunyun.meallog.food.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodAutoCompleteResponseDto {

    // 자동완성 검색 기능 -> 영양성분까지는 필요 없어서 자동완성 전용 dto 따로 만들기
    private Long id;
    private String name;
}


