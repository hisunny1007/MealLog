package com.yunyun.meallog.user.dao;

import com.yunyun.meallog.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserDao {
    int signUp(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    void updateProfile(User user);
    void updateRewardPoint(@Param("id") long id, @Param("rewardPoint") int rewardPoint);
}
