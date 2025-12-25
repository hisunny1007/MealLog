-- 0. 데이터베이스 초기화 (필요 시 주석 해제 후 사용)
-- CREATE DATABASE IF NOT EXISTS meal_log;
-- USE meal_log;

-- 1. 기존 테이블 삭제 (참조 역순)
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS foods;
DROP TABLE IF EXISTS users;

-- 2. users 테이블 생성
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '사용자 로그인 이메일',
    password VARCHAR(255) NOT NULL COMMENT 'Bcrypt 해시된 비밀번호',
    nickname VARCHAR(50) NOT NULL,
    gender CHAR(1) COMMENT '성별 (M:남자, F:여자)',
    age INT,
    height FLOAT,
    weight FLOAT,
    exercise_frequency VARCHAR(50) COMMENT '운동 빈도',
    exercise_goal ENUM('DIET', 'MUSCLE') COMMENT '운동 목표 (다이어트/근육증가)',
    reward_point INT NOT NULL DEFAULT 0 COMMENT '현재 포인트 잔액',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 3. foods 테이블 생성 (meals에서 참조함)
CREATE TABLE foods (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    calories FLOAT NOT NULL,
    carbs FLOAT NOT NULL,
    protein FLOAT NOT NULL,
    fat FLOAT NOT NULL,
    is_custom BOOLEAN NOT NULL DEFAULT FALSE,
    created_by BIGINT NULL,
    created_at DATETIME NULL,
    INDEX idx_food_name (name)
);

-- 4. products 테이블 생성 (orders에서 참조함)
CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '상품 PK',
    name VARCHAR(255) NOT NULL COMMENT '상품명',
    description TEXT NOT NULL COMMENT '상품 설명',
    price_point INT NOT NULL COMMENT '상품 가격 (포인트)',
    image_url VARCHAR(255) NOT NULL COMMENT '상품 이미지',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    category VARCHAR(50) NOT NULL COMMENT '상품 카테고리'
);

-- 5. meals 테이블 생성 (users 및 foods 참조)
CREATE TABLE meals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    date DATE NOT NULL,
    meal_type VARCHAR(10) NOT NULL,
    food_id BIGINT NULL,
    food_name VARCHAR(50) NOT NULL,
    memo VARCHAR(255),
    image_url VARCHAR(255),
    score TINYINT,
    calories FLOAT NOT NULL,
    carbs FLOAT NOT NULL,
    protein FLOAT NOT NULL,
    fat FLOAT NOT NULL,
    created_at DATETIME DEFAULT NOW(),
    CONSTRAINT uk_user_date_meal UNIQUE (user_id, date, meal_type),
    CONSTRAINT fk_meals_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_meals_foods FOREIGN KEY (food_id) REFERENCES foods(id)
);

-- 6. orders 테이블 생성 (users 및 products 참조)
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '주문 PK',
    user_id BIGINT NOT NULL COMMENT '유저 ID (FK)', 
    product_id BIGINT NOT NULL COMMENT '상품 ID (FK)',
    total_point INT NOT NULL COMMENT '총 사용 포인트',
    amount INT NOT NULL COMMENT '주문 수량',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '구매일',
    CONSTRAINT fk_orders_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_orders_product_id FOREIGN KEY (product_id) REFERENCES products(id)
);