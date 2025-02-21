package tn.esprit.leveltest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.leveltest.Dto.QuestionDTO;
import tn.esprit.leveltest.Dto.TestDTO;
import tn.esprit.leveltest.Dto.TestSubmissionDTO;
import tn.esprit.leveltest.Entity.Test;
import tn.esprit.leveltest.repo.TestRepository;

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
                test.getQuestions().stream().map(q -> new QuestionDTO(
                        q.getId(),
                        q.getQuestionText(),
                        q.getAnswerOptions(),
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
                test.getQuestions().stream().map(q -> new QuestionDTO(
                        q.getId(),
                        q.getQuestionText(),
                        q.getAnswerOptions(),
                        q.getCorrectAnswer()
                )).collect(Collectors.toList())
        ));
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
    public double ManageTimeTest(TestSubmissionDTO submission) {
        Test test = testRepository.findById(submission.getTestId())
                .orElseThrow(() -> new RuntimeException("Test not found"));

        LocalDateTime endTime = submission.getStartTime().plusMinutes(test.getDurationMinutes());
        if (LocalDateTime.now().isAfter(endTime)) {
            throw new RuntimeException("Temps écoulé ! Test non validé.");
        }

        return super.gradeTest(submission);
    }

}
