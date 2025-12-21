
package com.yunyun.meallog.pointshop.service.impl;

import com.yunyun.meallog.global.exception.CustomException;
import com.yunyun.meallog.global.exception.ErrorCode;
import com.yunyun.meallog.pointshop.dao.OrderDao;
import com.yunyun.meallog.pointshop.dao.ProductDao;
import com.yunyun.meallog.pointshop.domain.Order;
import com.yunyun.meallog.pointshop.domain.Product;
import com.yunyun.meallog.pointshop.dto.OrderHistoryDto;
import com.yunyun.meallog.pointshop.dto.OrderRequestDto;
import com.yunyun.meallog.pointshop.dto.OrderResponseDto;
import com.yunyun.meallog.pointshop.dto.ProductViewDto;
import com.yunyun.meallog.pointshop.service.PointShopService;
import com.yunyun.meallog.user.dao.UserDao;
import com.yunyun.meallog.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointShopServiceImpl implements PointShopService {

    private final ProductDao productDao;
    private final UserDao userDao;
    private final OrderDao orderDao;

    @Override
    public List<ProductViewDto> getProducts(String category) {
        List<Product> products = productDao.selectProductsByCategory(category);
        if (products == null) {
            return new java.util.ArrayList<>();
        }
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponseDto processPointExchange(int userId, OrderRequestDto dto) {
        Product product = productDao.selectProductById(dto.getProductId());
        if (product == null) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        int totalPointsNeeded = product.getPricePoint() * dto.getAmount();

        User user = userDao.findById((Integer) userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (user.getRewardPoint() < totalPointsNeeded) {
            throw new CustomException(ErrorCode.INSUFFICIENT_POINTS);
        }

        int newPoint = user.getRewardPoint() - totalPointsNeeded;
        userDao.updateRewardPoint(userId, newPoint);

        Order order = new Order(0, userId, dto.getProductId(), totalPointsNeeded, dto.getAmount(), null);

        orderDao.insertOrder(order);

        return new OrderResponseDto(order.getId(), product.getName(), newPoint);
    }

    @Override
    public List<OrderHistoryDto> getOrderHistory(int userId) {
        return orderDao.selectOrderHistoryByUserId(userId);
    }

    private ProductViewDto convertToDto(Product product) {
        return new ProductViewDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPricePoint(),
                product.getImageUrl(),
                product.getCategory()
        );
    }
}

