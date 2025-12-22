package com.yunyun.meallog.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunyun.meallog.meal.dto.request.MealRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.List;

@Service
public class AiService {

    private final String gmsKey;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AiService(@Value("${gms.api.key}") String gmsKey) {
        this.gmsKey = gmsKey;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        System.out.println("=== AiService 생성됨 ===");
        System.out.println("API Key 주입 여부: " + (gmsKey != null));
        System.out.println("API Key 길이: " + (gmsKey != null ? gmsKey.length() : 0));
    }

    public AiAnalysisResponse analyzeMeals(List<MealRequestDto> meals) {
        System.out.println("=== analyzeMeals 호출됨 ===");
        System.out.println("받은 meals 개수: " + meals.size());

        String prompt = buildPrompt(meals);
        System.out.println("생성된 prompt: " + prompt);

        String aiResult = callGmsApi(prompt);
        return parseAiResult(aiResult);
    }


    private String buildPrompt(List<MealRequestDto> meals) {
        StringBuilder sb = new StringBuilder();
        sb.append("다음 식단 정보를 분석하고, 반드시 JSON 형식으로만 응답해주세요.\n\n");

        sb.append("식단 정보:\n");
        for(MealRequestDto meal : meals) {
            sb.append("- ").append(meal.getMealType()).append(": ")
                    .append(meal.getFoodName())
                    .append(" (칼로리: ").append(meal.getCalories())
                    .append(", 탄수화물: ").append(meal.getCarbs())
                    .append(", 단백질: ").append(meal.getProtein())
                    .append(", 지방: ").append(meal.getFat()).append(")\n");
        }

        sb.append("\n");
        sb.append("응답 규칙:\n");
        sb.append("1. 반드시 아래 JSON 형식으로만 응답하세요.\n");
        sb.append("2. 어떠한 설명이나 추가 텍스트도 포함하지 마세요.\n");
        sb.append("3. 마크다운 코드 블록(```)을 사용하지 마세요.\n");
        sb.append("4. 순수 JSON만 반환하세요.\n\n");

        sb.append("JSON 형식:\n");
        sb.append("{\n");
        sb.append("  \"analysis\": \"식단에 대한 전반적인 분석 (한국어, 2-3문장)\",\n");
        sb.append("  \"recommendations\": [\n");
        sb.append("    \"추천 음식 1\",\n");
        sb.append("    \"추천 음식 2\",\n");
        sb.append("    \"추천 음식 3\"\n");
        sb.append("  ]\n");
        sb.append("}");

        return sb.toString();
    }

    private String callGmsApi(String prompt) {
        System.out.println("=== callGmsApi 시작 ===");
        String url = "https://gms.ssafy.io/gmsapi/api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        System.out.println("GMS API Key: " + gmsKey);
        headers.set("Authorization", "Bearer " + gmsKey);

        // JSON 직접 만들지 말고 Map으로 만들어서 ObjectMapper로 변환
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-4o-mini");

            List<Map<String, String>> messages = new ArrayList<>();

            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are a nutrition assistant. You MUST respond ONLY with valid JSON. Do not include any markdown code blocks, explanations, or additional text. Return pure JSON only.");
            messages.add(systemMessage);

            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);  // 알아서 이스케이프 됨!
            messages.add(userMessage);

            requestBody.put("messages", messages);

            String requestJson = objectMapper.writeValueAsString(requestBody);
            System.out.println("Request JSON: " + requestJson);

            HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

            System.out.println("GMS API 호출 중...");
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            System.out.println("GMS API 응답 성공!");
            System.out.println("Response: " + response.getBody());
            return response.getBody();
        } catch (Exception e) {
            System.err.println("GMS API 호출 실패!");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private AiAnalysisResponse parseAiResult(String aiResult) {
        try {
            JsonNode root = objectMapper.readTree(aiResult);
            JsonNode messageNode = root.path("choices").get(0).path("message").path("content");

            String content = messageNode.asText();
            System.out.println("=== Original content ===");
            System.out.println(content);

            // 모든 마크다운 코드 블록 제거
            content = content.replaceAll("```json\\s*", "")
                    .replaceAll("```\\s*", "")
                    .trim();

            // JSON이 시작되는 { 찾기
            int jsonStart = content.indexOf("{");
            // JSON이 끝나는 } 찾기
            int jsonEnd = content.lastIndexOf("}");

            if (jsonStart != -1 && jsonEnd != -1 && jsonEnd > jsonStart) {
                content = content.substring(jsonStart, jsonEnd + 1);
            }

            System.out.println("=== Cleaned content ===");
            System.out.println(content);

            JsonNode contentNode = objectMapper.readTree(content);

            String analysis = contentNode.path("analysis").asText();
            List<String> recommendations = objectMapper.convertValue(
                    contentNode.path("recommendations"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, String.class)
            );

            return new AiAnalysisResponse(analysis, recommendations);
        } catch (Exception e) {
            System.err.println("파싱 실패!");
            e.printStackTrace();
            return new AiAnalysisResponse("AI 분석 실패", List.of());
        }
    }
}