package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.dto.QuestionDTO;
import com.userPI.usersmanagementsystem.entity.Question;
import com.userPI.usersmanagementsystem.entity.Test;
import com.userPI.usersmanagementsystem.repository.QuestionRepository;
import com.userPI.usersmanagementsystem.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService implements IQuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestRepository testRepository;

    public List<QuestionDTO> getAllQuestions() {
        return questionRepository.findAll().stream().map(question -> new QuestionDTO(
                question.getId(),
                question.getQuestionText(),
                question.getAnswerOptions(),
                question.getCorrectAnswer()
                // ✅ Include Test Title
        )).collect(Collectors.toList());
    }

    public QuestionDTO createQuestion(QuestionDTO questionDTO, Long testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        Question question = new Question();
        question.setQuestionText(questionDTO.getQuestionText());
        question.setAnswerOptions(questionDTO.getAnswerOptions());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());
        question.setTest(test);

        Question savedQuestion = questionRepository.save(question);
        return new QuestionDTO(
                savedQuestion.getId(),
                savedQuestion.getQuestionText(),
                savedQuestion.getAnswerOptions(),
                savedQuestion.getCorrectAnswer()
                // ✅ Include Test Title
        );
    }

    @Override
    public List<QuestionDTO> getQuestionsByCategory(String category) {
        return List.of();
    }
}
