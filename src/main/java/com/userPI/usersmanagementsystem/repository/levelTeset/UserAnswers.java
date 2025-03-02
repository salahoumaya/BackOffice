package com.userPI.usersmanagementsystem.repository.levelTeset;

import com.userPI.usersmanagementsystem.entity.levelTest.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnswers  extends JpaRepository<UserAnswer, Long> {
}
