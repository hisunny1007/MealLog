package com.yunyun.meallog.global.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.yunyun.meallog.*.dao")
@Configuration
public class MyBatisConfig {

}