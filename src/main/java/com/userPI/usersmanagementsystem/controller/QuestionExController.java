package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.entity.Examen;
import com.userPI.usersmanagementsystem.entity.QuestionEx;
import com.userPI.usersmanagementsystem.repository.ExamenRepository;
import com.userPI.usersmanagementsystem.repository.QuestionExRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatbot/questions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class QuestionExController {

    private final QuestionExRepository questionRepo;
    private final ExamenRepository examRepo;

    @PostMapping("/{examId}")
    public ResponseEntity<QuestionEx> addQuestionToExam(
            @PathVariable Long examId, @RequestBody QuestionEx question) {
        Examen exam = examRepo.findById(examId).orElseThrow();
        question.setExam(exam);
        return ResponseEntity.ok(questionRepo.save(question));
    }
    @GetMapping("/{id}/full")
    public ResponseEntity<Examen> getExamWithQuestionsAndAnswers(@PathVariable Long id) {
        Examen exam = examRepo.findById(id).orElseThrow();
        exam.getQuestions().forEach(q -> q.getAnswers().size());
        return ResponseEntity.ok(exam);
    }

    @DeleteMapping("/{examId}")
    public  String delete(
            @PathVariable Long examId) {
        examRepo.deleteById(examId);
return "deleted";
    }

}
