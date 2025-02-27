package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.dto.AnswerDTO;
import com.userPI.usersmanagementsystem.dto.QuestionDTO;
import com.userPI.usersmanagementsystem.dto.TestDTO;
import com.userPI.usersmanagementsystem.dto.TestSubmissionDTO;
import com.userPI.usersmanagementsystem.entity.Question;
import com.userPI.usersmanagementsystem.entity.Test;
import com.userPI.usersmanagementsystem.repository.QuestionRepository;
import com.userPI.usersmanagementsystem.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;
    private QuestionRepository questionRepository;

    public List<TestDTO> getAllTests() {
        return testRepository.findAll().stream().map(test -> new TestDTO(
                test.getId(),
                test.getTitle(),
                test.getDescription(),
                test.getScheduledAt(),
                test.getDuration(),
                test.getScore(),
                test.getQuestions().stream().map(q -> new QuestionDTO(
                        q.getId(),
                        q.getQuestionText(),
                        q.getOptionA(),
                        q.getOptionB(),
                        q.getOptionC(),
                        q.getOptionD(),
                        q.getCorrectAnswer()
                )).collect(Collectors.toList())
        )).collect(Collectors.toList());
    }

    public Optional<TestDTO> getTestById(Long id) {
        return testRepository.findById(id).map(test -> new TestDTO(
                test.getId(),
                test.getTitle(),
                test.getDescription(),
                test.getScheduledAt(),
                test.getDuration(),
                test.getScore(),
                test.getQuestions().stream().map(q -> new QuestionDTO(
                        q.getId(),
                        q.getQuestionText(),
                        q.getOptionA(),
                        q.getOptionB(),
                        q.getOptionC(),
                        q.getOptionD(),
                        q.getCorrectAnswer()
                )).collect(Collectors.toList())
        ));
    }

    public TestDTO createTest(TestDTO testDTO) {
        Test test = new Test();
        test.setTitle(testDTO.getTitle());
        test.setDescription(testDTO.getDescription());
        test.setScheduledAt(testDTO.getScheduledAt());
        test.setDuration(testDTO.getDuration());
        test.setScore(testDTO.getScore());

        Test savedTest = testRepository.save(test);
        return new TestDTO(savedTest.getId(), savedTest.getTitle(), savedTest.getDescription(), savedTest.getScheduledAt(), savedTest.getDuration(), savedTest.getScore(), null);
    }
    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }
    public double evaluateTest(TestSubmissionDTO submissionDTO) {
        Test test = testRepository.findById(submissionDTO.getTestId())
                .orElseThrow(() -> new RuntimeException("Test introuvable"));

        int totalQuestions = test.getQuestions().size();
        int correctAnswers = 0;

        for (AnswerDTO answer : submissionDTO.getAnswers()) {
            Question question = questionRepository.findById(answer.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question introuvable"));

            if (question.getCorrectAnswer().equalsIgnoreCase(answer.getUserAnswer())) {
                correctAnswers++;
            }
        }

        // Calcul du score en pourcentage
        double score = ((double) correctAnswers / totalQuestions) * test.getScore();
        return score;
    }

}
