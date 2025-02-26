package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.dto.QuestionDTO;
import com.userPI.usersmanagementsystem.dto.TestDTO;
import com.userPI.usersmanagementsystem.entity.Test;
import com.userPI.usersmanagementsystem.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestService implements ITestService {
    @Autowired
    private TestRepository testRepository;

    public List<TestDTO> getAllTests() {
        return testRepository.findAll().stream().map(test -> new TestDTO(
                test.getId(),
                test.getTitle(),
                test.getDescription(),
                test.getScheduledAt(),
                test.getQuestions() != null ? test.getQuestions().stream().map(q -> new QuestionDTO(
                        q.getId(),
                        q.getQuestionText(),
                        q.getAnswerOptions(),
                        q.getCorrectAnswer()
                )).collect(Collectors.toList()) : null  // ✅ Prevents NullPointerException
        )).collect(Collectors.toList());
    }

    public Optional<TestDTO> getTestById(Long id) {
        return testRepository.findById(id).map(test -> {
            List<QuestionDTO> questionDTOs = test.getQuestions() != null ?
                    test.getQuestions().stream().map(q -> new QuestionDTO(
                            q.getId(),
                            q.getQuestionText(),
                            q.getAnswerOptions(),
                            q.getCorrectAnswer()
                    )).collect(Collectors.toList()) : null;

            return new TestDTO(
                    test.getId(),
                    test.getTitle(),
                    test.getDescription(),
                    test.getScheduledAt(),
                    questionDTOs
            );
        });
    }

    public TestDTO createTest(TestDTO testDTO) {
        Test test = new Test();
        test.setTitle(testDTO.getTitle());
        test.setDescription(testDTO.getDescription());
        test.setScheduledAt(testDTO.getScheduledAt());

        Test savedTest = testRepository.save(test);
        return new TestDTO(savedTest.getId(), savedTest.getTitle(), savedTest.getDescription(), savedTest.getScheduledAt(), null);
    }

    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }

    @Override
    public double getTestSuccessRate(Long testId) {
        return 0;
    }

    @Override
    public boolean isTestTimeExpired(Long testId, Long studentId, LocalDateTime startTime) {
        return false;
    }

    @Override
    public byte[] exportTestResultsToExcel(Long testId) {
        return new byte[0];
    }
}
