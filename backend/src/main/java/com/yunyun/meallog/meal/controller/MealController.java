package com.yunyun.meallog.meal.controller;

import com.yunyun.meallog.meal.dto.request.MealRequestDto;
import com.yunyun.meallog.meal.dto.response.MealCalendarSummaryResponseDto;
import com.yunyun.meallog.meal.dto.response.MealCreateResponseDto;
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
    // @RequestPart : multipart/form-data 형식으로 전송된 요청에서 파일과 JSON 데이터를 동시에 처리할 때 사용

    // 식단 등록 시 이미지 함께 업로드 + 이미지 없을 시 랜덤 이미지 제공
    @PostMapping
    public ResponseEntity<MealCreateResponseDto> createMeal(
            @RequestAttribute("userId") Long userId,
            @RequestPart(value = "data") MealRequestDto requestDto,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        MealCreateResponseDto response = mealService.createMeal(userId, requestDto, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
    @GetMapping("/date/{date}")
    public ResponseEntity<List<MealResponseDto>> getMealsByDate(
            @RequestAttribute("userId") Long userId,
            @PathVariable LocalDate date) {
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
            @RequestPart(value = "data") MealRequestDto requestDto,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        // 이미지 같이 받으려면 @RequestBody 아니고 @RequestPart로
        MealResponseDto response = mealService.updateMeal(userId, mealId, requestDto, image);
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

    //캘린더 요약 표시용 api
    @GetMapping("/calendar")
    public List<MealCalendarSummaryResponseDto> getCalendarSummary(
            @RequestAttribute("userId") Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        return mealService.getCalendarSummary(userId, year, month);
    }
}
