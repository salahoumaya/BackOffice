package tn.esprit.leveltest.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.leveltest.Dto.QuestionDTO;
import tn.esprit.leveltest.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping
    public List<QuestionDTO> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @PostMapping("/{testId}")
    public QuestionDTO createQuestion(@PathVariable Long testId, @RequestBody QuestionDTO questionDTO) {
        return questionService.createQuestion(questionDTO, testId);
    }
}
