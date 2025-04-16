package com.userPI.usersmanagementsystem.repository;

import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.entity.user.UserFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

public interface UserFeedbackRepo extends JpaRepository<UserFeedback, Long> {
    Optional<UserFeedback> findByUserIdAndTrainingId(Integer userId, Integer trainingId);
    List<UserFeedback> findByUserId(int userId);






}