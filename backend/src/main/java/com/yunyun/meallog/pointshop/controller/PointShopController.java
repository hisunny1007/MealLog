package com.yunyun.meallog.pointshop.controller;

import com.yunyun.meallog.global.jwt.JwtUtil;
import com.yunyun.meallog.pointshop.dto.OrderRequestDto;
import com.yunyun.meallog.pointshop.dto.OrderResponseDto;
import com.yunyun.meallog.pointshop.dto.ProductViewDto;
import com.yunyun.meallog.pointshop.service.PointShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pointshop")
@RequiredArgsConstructor
public class PointShopController {

    private final PointShopService pointShopService;
    private final JwtUtil jwtUtil;

    @GetMapping("/products")
    public ResponseEntity<List<ProductViewDto>> getProducts(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(pointShopService.getProducts(category));
    }

    @PostMapping("/purchase")
    public ResponseEntity<OrderResponseDto> purchase(@RequestHeader("Authorization") String token, @RequestBody OrderRequestDto orderRequestDto) {
        String jwt = token.substring(7);
        int userId = Integer.parseInt(String.valueOf(jwtUtil.getUserId(jwt)));
        return ResponseEntity.ok(pointShopService.processPointExchange(userId, orderRequestDto));
    }

    @GetMapping("/history")
    public ResponseEntity<?> getOrderHistory(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        int userId = Integer.parseInt(String.valueOf(jwtUtil.getUserId(jwt)));

        return ResponseEntity.ok(pointShopService.getOrderHistory(userId));
    }
}
