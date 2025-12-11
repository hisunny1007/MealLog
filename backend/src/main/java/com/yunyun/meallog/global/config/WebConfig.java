package com.yunyun.meallog.global.config;

import com.yunyun.meallog.global.interceptor.AdminInterceptor;
import com.yunyun.meallog.global.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    AdminInterceptor adminInterceptor;

    @Autowired
    JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // JWT 인터셉터를 먼저 등록하여 대부분의 경로에서 토큰을 검증
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/users/login",       // 로그인 경로는 토큰 검증에서 제외
                        "/users/signup",      // 회원가입 경로도 토큰 검증에서 제외
                        "/swagger-ui/**",     // Swagger UI 관련 리소스는 제외
                        "/v3/api-docs/**"     // API 문서 자동 생성 관련 경로도 제외
                );


        // "/users" 경로로 요청이 오면 JwtInterceptor가 먼저 실행된 후, AdminInterceptor가 실행
        registry.addInterceptor(adminInterceptor).addPathPatterns("/users");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST")
                .allowedHeaders("*");
    }
}