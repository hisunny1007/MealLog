package com.yunyun.meallog.global.config;

import com.yunyun.meallog.global.interceptor.AdminInterceptor;
import com.yunyun.meallog.global.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    AdminInterceptor adminInterceptor;

    @Autowired
    JwtInterceptor jwtInterceptor;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/users/login",
                        "/users/signup",
                        "/users/*/profile",
                        "/pointshop/**",
                        "/users/emailcheck",
                        "/users/checknickname/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/api/v1/uploads/**",
                        "/uploads/**", // 이미지 엑박 수정
                        "/ai/**"
                );

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/api/v1/users");


    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("*");
    }

    // spring에서 이미지 폴더를 정적 리소스로 열어주기
    // /uploads/파일명 요청이 오면 서버의 실제 uploads 폴더에서 파일 찾아서 변환함
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absolutePath = Paths.get(uploadDir).toAbsolutePath().toString();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + absolutePath + "/");
    }
}