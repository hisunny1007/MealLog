package com.yunyun.meallog.meal.service;

import com.yunyun.meallog.meal.dto.request.MealRequestDto;
import com.yunyun.meallog.meal.dto.response.MealCalendarSummaryResponseDto;
import com.yunyun.meallog.meal.dto.response.MealCreateResponseDto;
import com.yunyun.meallog.meal.dto.response.MealResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;


public interface MealService {

    //service는 dto주고받는 레이어라 엔티티 대신 dto 반환이 더 바람직함

    MealCreateResponseDto createMeal(Long userId, MealRequestDto requestDto, MultipartFile image);

    MealResponseDto getMeal(Long userId, Long mealId);

    List<MealResponseDto> getMealsByDate(Long userId, LocalDate date);

    MealResponseDto updateMeal(Long userId, Long mealId, MealRequestDto requestDto, MultipartFile image);

    void deleteMeal(Long userId, Long mealId);

    // 캘린더 요약 표시용
    List<MealCalendarSummaryResponseDto> getCalendarSummary(Long userId, int year, int month);

    // AI 추천용 - 최근 7일 식단 메뉴 이름 조회
    List<String> getFoodNamesLastSevenDays(Long userId);
}
