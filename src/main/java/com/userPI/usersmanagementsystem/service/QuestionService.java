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
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestRepository testRepository;

    public List<QuestionDTO> getAllQuestions() {
        return questionRepository.findAll().stream().map(question -> new QuestionDTO(
                question.getId(),
                question.getQuestionText(),
                question.getOptionA(),
                question.getOptionB(),
                question.getOptionC(),
                question.getOptionD(),
                question.getCorrectAnswer()
        )).collect(Collectors.toList());
    }

    public QuestionDTO createQuestion(QuestionDTO questionDTO, Long testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test non trouvé"));

        // Validation : au moins 3 options doivent être remplies
        if (questionDTO.getOptionA().isEmpty() || questionDTO.getOptionB().isEmpty() || questionDTO.getOptionC().isEmpty()) {
            throw new RuntimeException("Une question doit avoir au moins trois options");
        }

        // Validation : la réponse correcte doit être l'une des options
        if (!questionDTO.getCorrectAnswer().equals(questionDTO.getOptionA()) &&
                !questionDTO.getCorrectAnswer().equals(questionDTO.getOptionB()) &&
                !questionDTO.getCorrectAnswer().equals(questionDTO.getOptionC()) &&
                (questionDTO.getOptionD() == null || !questionDTO.getCorrectAnswer().equals(questionDTO.getOptionD()))) {
            throw new RuntimeException("La réponse correcte doit être l'une des options fournies");
        }

        Question question = new Question();
        question.setQuestionText(questionDTO.getQuestionText());
        question.setOptionA(questionDTO.getOptionA());
        question.setOptionB(questionDTO.getOptionB());
        question.setOptionC(questionDTO.getOptionC());
        question.setOptionD(questionDTO.getOptionD());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());
        question.setTest(test);

        Question savedQuestion = questionRepository.save(question);
        return new QuestionDTO(
                savedQuestion.getId(),
                savedQuestion.getQuestionText(),
                savedQuestion.getOptionA(),
                savedQuestion.getOptionB(),
                savedQuestion.getOptionC(),
                savedQuestion.getOptionD(),
                savedQuestion.getCorrectAnswer());
    }
}
