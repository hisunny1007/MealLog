package com.yunyun.meallog.food.controller;

import com.yunyun.meallog.food.dto.request.FoodRequestDto;
import com.yunyun.meallog.food.dto.response.FoodResponseDto;
import com.yunyun.meallog.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/foods")
public class  FoodController {

    private final FoodService foodService;

    // 자동완성 검색
    @GetMapping("/auto-complete")
    public List<FoodResponseDto> searchAutoComplete(@RequestParam String query) {
        return foodService.autoComplete(query);
    }

    // 음식 상세 검색 (자동완성 결과 중 하나 선택하면 해당 음식 상세정보 조회)
    @GetMapping("/search")
    public List<FoodResponseDto> searchFood(@RequestParam String query) {
        return foodService.searchFood(query);
    }

    //  음식 상세 조회 (foodId로 요청)
    // 식단 기록 시 음식 선택하면 ID로 상세정보 가져올 때 사용
    @GetMapping("/{foodId}")
    public FoodResponseDto getFoodDetail(@PathVariable Long foodId) {
        return foodService.getFoodDetail(foodId);
    }

    // 음식 직접 추가 (커스텀 음식 등록)
    @PostMapping
    //is_custom = true
    //created_by = userId
    public void createCustomFood(
            @RequestAttribute("userId") Long userId,
            @RequestBody FoodRequestDto requestDto) {
        foodService.createCustomFood(userId, requestDto);
    }
}
