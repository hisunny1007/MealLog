package com.yunyun.meallog.ai;

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
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

    @PostMapping("/analyze")
    public ResponseEntity<AiAnalysisResponse> analyzeMeal(@RequestBody List<MealRequestDto> meals) {

        // 단순 상태 분석이니까 userId는 필요없음
        AiAnalysisResponse response = aiService.analyzeMeals(meals);

        return ResponseEntity.ok(response);
    }
}
