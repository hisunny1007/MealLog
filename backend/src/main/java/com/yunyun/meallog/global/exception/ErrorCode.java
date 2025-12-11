package com.yunyun.meallog.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C004", "Server Error"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C006", "Access is Denied"),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "User is not found."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "U002", "email 또는 password를 확인해보세요."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "U003", "이미 사용중인 이메일입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A001", "유효하지 않은 토큰입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}