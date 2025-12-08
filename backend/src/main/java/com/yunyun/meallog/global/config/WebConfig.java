package com.yunyun.meallog.global.config;

import com.yunyun.meallog.global.MockUserFilter;
import com.yunyun.meallog.global.interceptor.AdminInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Autowired
    AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor).addPathPatterns("/users");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST");

    }

    //테스트용 임시 userId 강제 주입 (유저 기능 완성 전 임시로 사용)
    @Bean
    public FilterRegistrationBean<MockUserFilter> mockUserFilter() {
        FilterRegistrationBean<MockUserFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new MockUserFilter());
        registration.addUrlPatterns("/meals/*"); // meal API 테스트만 적용
        registration.setOrder(1);
        return registration;
    }
}