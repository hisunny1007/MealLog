package com.yunyun.meallog.ai;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AiAnalysisResponse {
    private String analysis; // 오늘 식단 분석 코멘트
    private List<String> recommendations; // 추천 음식 리스트
}