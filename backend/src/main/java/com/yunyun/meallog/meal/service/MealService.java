package com.yunyun.meallog.meal.service;

import com.yunyun.meallog.meal.dto.request.MealRequestDto;
import com.yunyun.meallog.meal.dto.response.MealResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;


public interface MealService {

    //service는 dto주고받는 레이어라 엔티티 대신 dto 반환이 더 바람직함

    MealResponseDto createMeal(Long userId, MealRequestDto requestDto);

    MealResponseDto getMeal(Long userId, Long mealId);

    List<MealResponseDto> getMealsByDate(Long userId, LocalDate date);

    MealResponseDto updateMeal(Long userId, Long mealId, MealRequestDto requestDto);

    void deleteMeal(Long userId, Long mealId);

    // 이미지 업로드
    MealResponseDto createMealWithImage(Long userId, MealRequestDto requestDto, MultipartFile image);
}
