package com.yunyun.meallog.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI OpenAPI() {
        return new OpenAPI()
                .info(new Info().title("MealLog REST API")
                        .description("MealLog 프로젝트의 API 문서")
                        .version("v1.0.0")
                        .license(new License().name("meal_log").url("https://www.ssafy.com")));
    }

}