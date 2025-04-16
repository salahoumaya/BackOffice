package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.entity.levelTest.TestSubmission;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.entity.user.UserFeedback;
import com.userPI.usersmanagementsystem.repository.UserFeedbackRepo;
import com.userPI.usersmanagementsystem.repository.levelTeset.TestSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class Recommendation {
    @Autowired
    TestSubmissionRepository testSubmissionRepository;
    @Autowired
    UserFeedbackRepo userFeedbackRepository;
    @Autowired
    RestTemplate restTemplate;


        public List<Map<String, Object>> getRecommendations (Integer userId){
            // 1. Récupérer le dernier test soumis
            TestSubmission latestSubmission = testSubmissionRepository
                    .findTopByUserIdOrderBySubmittedAtDesc(userId)
                    .orElseThrow(() -> new RuntimeException("Aucun test soumis par cet utilisateur"));

            double quizScore = latestSubmission.getScore();

            // 2. Récupérer les feedbacks de l'utilisateur
            List<UserFeedback> feedbacks = userFeedbackRepository.findByUserId(userId);

            // Construire la liste des feedbacks au format attendu
            List<Map<String, Object>> feedbackList = feedbacks.stream()
                    .map(fb -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("trainingName", fb.getTraining().getTitle());
                        map.put("rating", fb.getRating());
                        map.put("comment", fb.getComment());
                        return map;
                    })
                    .collect(Collectors.toList());

            // 3. Construire la requête JSON
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("quiz_score", quizScore);
            requestBody.put("feedbacks", feedbackList);

            // 4. Appeler l'API FastAPI
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "http://localhost:8002/recommend/existing-user",
                    requestBody,
                    Map.class
            );

            // 5. Extraire et retourner les recommandations
            return (List<Map<String, Object>>) response.getBody().get("recommendations");
        }
    }

