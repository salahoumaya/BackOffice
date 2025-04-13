package com.userPI.usersmanagementsystem.repository.levelTeset;

import com.userPI.usersmanagementsystem.entity.levelTest.Question;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, Long> {
}
