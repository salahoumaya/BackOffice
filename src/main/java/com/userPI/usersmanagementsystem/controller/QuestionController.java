package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.dto.QuestionDTO;
import com.userPI.usersmanagementsystem.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/questions")
@PreAuthorize("hasRole('ADMIN')") // 🔒 Sécurisation des endpoints admin
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // ✅ Récupérer toutes les questions
    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    // ✅ Ajouter une question et l'affecter à un test
    @PostMapping("/{testId}")
    public ResponseEntity<QuestionDTO> createQuestion(@PathVariable Long testId, @Valid @RequestBody QuestionDTO questionDTO) {
        return ResponseEntity.ok(questionService.createQuestion(questionDTO, testId));
    }
}
