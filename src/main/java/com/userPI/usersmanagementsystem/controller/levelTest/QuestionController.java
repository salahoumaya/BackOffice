package com.userPI.usersmanagementsystem.controller.levelTest;

import com.userPI.usersmanagementsystem.dto.levelTest.QuestionDTO;
import com.userPI.usersmanagementsystem.service.TesLevel.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/questions")
@PreAuthorize("hasRole('ADMIN')")
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }


    @PostMapping("/{testId}")
    public ResponseEntity<QuestionDTO> createQuestion(@PathVariable Long testId, @Valid @RequestBody QuestionDTO questionDTO) {
        return ResponseEntity.ok(questionService.createQuestion(questionDTO, testId));
    }
}
