package com.yunyun.meallog.global.interceptor;

import com.yunyun.meallog.global.exception.CustomException;
import com.yunyun.meallog.global.exception.ErrorCode;
import com.yunyun.meallog.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.logging.LogException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if("OPTIONS".equals(request.getMethod())){
            return true;
        }
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String token = authHeader.substring(7);

        if(!jwtUtil.isTokenValid(token)){
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String userId = jwtUtil.getUserId(token);
        request.setAttribute("userId", Long.parseLong(userId));

        return true;
    }

}
