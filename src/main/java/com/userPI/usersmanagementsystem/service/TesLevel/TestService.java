package com.userPI.usersmanagementsystem.service.TesLevel;

import com.userPI.usersmanagementsystem.dto.levelTest.AnswerDTO;
import com.userPI.usersmanagementsystem.dto.levelTest.QuestionDTO;
import com.userPI.usersmanagementsystem.dto.levelTest.TestDTO;
import com.userPI.usersmanagementsystem.dto.levelTest.TestSubmissionDTO;
import com.userPI.usersmanagementsystem.entity.levelTest.Question;
import com.userPI.usersmanagementsystem.entity.levelTest.Test;
import com.userPI.usersmanagementsystem.entity.levelTest.TestSubmission;
import com.userPI.usersmanagementsystem.entity.levelTest.UserAnswer;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import com.userPI.usersmanagementsystem.repository.levelTeset.QuestionRepository;
import com.userPI.usersmanagementsystem.repository.levelTeset.TestRepository;
import com.userPI.usersmanagementsystem.repository.levelTeset.TestSubmissionRepository;
import com.userPI.usersmanagementsystem.repository.levelTeset.UserAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestService {
    @Autowired
    TestRepository testRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    TestSubmissionRepository testSubmissionRepository;
    @Autowired
    UserAnswers userAnswers;


    @Autowired
    public TestService(TestRepository testRepository, QuestionRepository questionRepository) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
    }

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

    public double evaluateAndSaveTest(Integer userId, TestSubmissionDTO submissionDTO) {
        Test test = testRepository.findById(submissionDTO.getTestId())
                .orElseThrow(() -> new RuntimeException("Test introuvable"));

        OurUsers user = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        LocalDateTime currentTime = LocalDateTime.now();
        int totalQuestions = test.getQuestions().size();
        int correctAnswers = 0;


        Optional<TestSubmission> existingSubmissionOpt = testSubmissionRepository.findByUserAndTest(user, test);

        TestSubmission testSubmission;

        if (existingSubmissionOpt.isPresent()) {
            testSubmission = existingSubmissionOpt.get();

            if (testSubmission.getSubmittedAt() != null) {
                throw new RuntimeException("🚫 Ce test a déjà été soumis, vous ne pouvez plus soumettre à nouveau !");
            }


            long elapsedTime = java.time.Duration.between(testSubmission.getStartTime(), currentTime).toMinutes();
            if (elapsedTime > test.getDuration()) {
                throw new RuntimeException("⏳ Temps écoulé ! Vous ne pouvez plus soumettre ce test.");
            }
        } else {

            testSubmission = new TestSubmission();
            testSubmission.setUser(user);
            testSubmission.setTest(test);
            testSubmission.setStartTime(currentTime);
        }

        List<UserAnswer> userAnswers = new ArrayList<>();

        for (AnswerDTO answer : submissionDTO.getAnswers()) {
            Question question = questionRepository.findById(answer.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question introuvable"));

            boolean isCorrect = question.getCorrectAnswer().trim().equalsIgnoreCase(answer.getUserAnswer().trim());
            if (isCorrect) {
                correctAnswers++;
            }

            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setTestSubmission(testSubmission);
            userAnswer.setQuestion(question);
            userAnswer.setUserAnswer(answer.getUserAnswer());
            userAnswer.setIsCorrect(isCorrect);
            userAnswers.add(userAnswer);
        }

        double score = ((double) correctAnswers / totalQuestions) * test.getScore();
        testSubmission.setScore(score);
        testSubmission.setSubmittedAt(currentTime); // Marque la soumission comme terminée
        testSubmission.setUserAnswers(userAnswers);

        testSubmissionRepository.save(testSubmission);

        return score;
    }


    public List<TestSubmissionDTO> getTestSubmissions(Long testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test introuvable"));

        List<TestSubmission> submissions = testSubmissionRepository.findByTest(test);

        return submissions.stream().map(submission -> new TestSubmissionDTO(
                submission.getTest().getId(),  // ✅ Utiliser uniquement l'ID du test
                submission.getUserAnswers().stream().map(answer -> new AnswerDTO(
                        answer.getQuestion().getId(),
                        answer.getUserAnswer(),
                        answer.isCorrect()
                )).collect(Collectors.toList()),
                submission.getScore(),
                submission.getSubmittedAt(),
                submission.getStartTime()
        )).collect(Collectors.toList());
    }






    }
