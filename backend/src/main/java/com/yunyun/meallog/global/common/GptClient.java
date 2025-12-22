package com.yunyun.meallog.global.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class GptClient {
    private final String apiKey;
    private final String apiUrl;
    private final String model;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GptClient(
            @Value("${gpt.api.key}") String apiKey,
            @Value("${gpt.api.url}") String apiUrl,
            @Value("${gpt.model}") String model) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.model = model;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String analyzeMealPattern(String mealData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey.trim());
//        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);

            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of("role", "system", "content",
                    "You are a nutrition assistant. Respond ONLY with valid JSON in the format: {\"protein\": count, \"vegetable\": count, \"diet-box\": count}. Do not use markdown blocks or explanations."));
            messages.add(Map.of("role", "user", "content", "분석할 식단 리스트: " + mealData));

            requestBody.put("messages", messages);

            String requestJson = objectMapper.writeValueAsString(requestBody);
            HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

            return cleanJsonContent(extractContent(response.getBody()));
        } catch (Exception e) {
            log.error("GPT 호출 실패: {}", e.getMessage());
            return "{}";
        }
    }

    private String extractContent(String body) throws Exception {
        return objectMapper.readTree(body).path("choices").get(0).path("message").path("content").asText();
    }

    private String cleanJsonContent(String content) {
        if (content == null) return "{}";
        String cleaned = content.replaceAll("```json\\s*", "").replaceAll("```\\s*", "").trim();
        int start = cleaned.indexOf("{");
        int end = cleaned.lastIndexOf("}");
        return (start != -1 && end != -1) ? cleaned.substring(start, end + 1) : cleaned;
    }
}