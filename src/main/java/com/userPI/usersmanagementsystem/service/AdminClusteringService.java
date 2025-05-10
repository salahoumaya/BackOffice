package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.dto.UserClusterRequestDTO;
import com.userPI.usersmanagementsystem.dto.UserClusterResponseDTO;
import com.userPI.usersmanagementsystem.dto.UserClusterViewDTO;
import com.userPI.usersmanagementsystem.entity.levelTest.TestSubmission;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.entity.user.UserFeedback;
import com.userPI.usersmanagementsystem.repository.UserFeedbackRepo;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import com.userPI.usersmanagementsystem.repository.levelTeset.TestSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminClusteringService {
    private static final Logger logger = LoggerFactory.getLogger(AdminClusteringService.class);

    private final UsersRepo userRepository;
    private final TestSubmissionRepository testRepo;
    private final UserFeedbackRepo feedbackRepo;
    private final RestTemplate restTemplate;

    public List<UserClusterViewDTO> getAllUserClusters() {
        return userRepository.findAll().stream()
                .map(this::processUser)
                .filter(Objects::nonNull)
                .toList();
    }

    private UserClusterViewDTO processUser(OurUsers user) {
        try {

            UserFeedback feedback = feedbackRepo.findTopByUserOrderByIdDesc(user)
                    .orElseThrow(() -> new RuntimeException("Aucun feedback trouvé pour l'utilisateur " + user.getId()));

            TestSubmission test = testRepo.findTopByUserOrderBySubmittedAtDesc(user)
                    .orElseThrow(() -> new RuntimeException("Aucun test trouvé pour l'utilisateur " + user.getId()));


            double quizScore = test.getScore();
            int satisfaction = feedback.getRating();
            double quizPerformance = quizScore * satisfaction;



            double sessionDuration;
            double sessionsPerWeek;
            int activitySpan;

            if (satisfaction <= 2) {
                sessionDuration = 40.0;
                sessionsPerWeek = 1.0;
                activitySpan = 10;
            } else if (satisfaction <= 4) {
                sessionDuration = 100.0;
                sessionsPerWeek = 3.0;
                activitySpan = 30;
            } else {
                sessionDuration = 200.0;
                sessionsPerWeek = 5.0;
                activitySpan = 60;
            }

            double averageSessionDuration = sessionDuration / sessionsPerWeek;


            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("QuizScores", quizScore);
            requestBody.put("UserSatisfaction", satisfaction);
            requestBody.put("QuizPerformance", quizPerformance);
            requestBody.put("SessionDuration", sessionDuration);
            requestBody.put("SessionsPerWeek", sessionsPerWeek);
            requestBody.put("AverageSessionDuration", averageSessionDuration);
            requestBody.put("ActivitySpan", activitySpan);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);


            ResponseEntity<UserClusterResponseDTO> response = restTemplate.exchange(
                    "http://localhost:8004/predict-cluster",
                    HttpMethod.POST,
                    requestEntity,
                    UserClusterResponseDTO.class
            );

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new RuntimeException("Erreur de l'API: " + response.getStatusCode());
            }


            return new UserClusterViewDTO(
                    user.getId(),
                    user.getEmail(),
                    response.getBody().getCluster(),
                    response.getBody().getLabel(),
                    response.getBody().getCommentaire(),
                    satisfaction,
                    quizScore,
                    quizPerformance,
                    sessionDuration,
                    sessionsPerWeek,
                    averageSessionDuration,
                    activitySpan
            );

        } catch (Exception e) {
            logger.error("Erreur lors du traitement de l'utilisateur {}: {}", user.getId(), e.getMessage());
            return new UserClusterViewDTO(
                    user.getId(),
                    user.getEmail(),
                    -1,
                    "Erreur",
                    e.getMessage(),
                    0, 0.0, 0.0, 0.0, 0.0, 0.0, 0
            );
        }
    }
}