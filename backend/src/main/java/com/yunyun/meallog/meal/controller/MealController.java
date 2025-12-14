package com.yunyun.meallog.meal.controller;

import com.yunyun.meallog.meal.dto.request.MealRequestDto;
import com.yunyun.meallog.meal.dto.response.MealResponseDto;
import com.yunyun.meallog.meal.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meals")
public class MealController {

    private final MealService mealService;

    // 식단 등록
    //foodId 있으면 Food DB 값 사용
    //foodId 없으면 직접 입력 값 사용
    @PostMapping
    public ResponseEntity<MealResponseDto> createMeal(
            @RequestAttribute("userId") Long userId,
            @RequestBody MealRequestDto requestDto) {
        MealResponseDto response = mealService.createMeal(userId, requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // 식단 단건 조회
    @GetMapping("/{mealId}")
    public ResponseEntity<MealResponseDto> getMeal(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long mealId) {
        MealResponseDto response = mealService.getMeal(userId, mealId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // 특정 날짜 식단 조회
    // meals?date=2025-12-14
    @GetMapping
    public ResponseEntity<List<MealResponseDto>> getMealsByDate(
            @RequestAttribute("userId") Long userId,
            @RequestParam LocalDate date) {
        List<MealResponseDto> response = mealService.getMealsByDate(userId, date);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // 식단 수정
    @PutMapping("/{mealId}")
    public ResponseEntity<MealResponseDto> updateMeal(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long mealId,
            @RequestBody MealRequestDto requestDto) {
        MealResponseDto response = mealService.updateMeal(userId, mealId, requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // 식단 삭제
    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> deleteMeal(
            @RequestAttribute("userId") Long userId,
            @PathVariable Long mealId) {
        mealService.deleteMeal(userId, mealId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 이미지 업로드
    // @RequestPart : multipart/form-data 형식으로 전송된 요청에서 파일과 JSON 데이터를 동시에 처리할 때 사용
    @PostMapping("/images")
    public ResponseEntity<MealResponseDto> createMealWithImage(
            @RequestAttribute("userId") Long userId,
            @RequestPart(value = "data") MealRequestDto requestDto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        return ResponseEntity.ok(mealService.createMealWithImage(userId, requestDto, image));

    }
}
