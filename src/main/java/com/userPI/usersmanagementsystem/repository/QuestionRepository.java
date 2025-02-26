package com.userPI.usersmanagementsystem.repository;

import com.userPI.usersmanagementsystem.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, Long> {
}
