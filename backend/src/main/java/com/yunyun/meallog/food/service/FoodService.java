package com.yunyun.meallog.food.service;


import com.yunyun.meallog.food.dto.request.FoodRequestDto;
import com.yunyun.meallog.food.dto.response.FoodAutoCompleteResponseDto;
import com.yunyun.meallog.food.dto.response.FoodResponseDto;

import java.util.List;

public interface FoodService {

    List<FoodAutoCompleteResponseDto> autoComplete(String query);

    List<FoodResponseDto> searchFood(String query);

    FoodResponseDto getFoodDetail(Long id);

    FoodResponseDto createCustomFood(Long userId, FoodRequestDto requestDto);


}
