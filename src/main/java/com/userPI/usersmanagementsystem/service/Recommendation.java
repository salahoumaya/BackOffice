package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.entity.levelTest.TestSubmission;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.entity.user.UserFeedback;
import com.userPI.usersmanagementsystem.repository.UserFeedbackRepo;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import com.userPI.usersmanagementsystem.repository.levelTeset.TestSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class Recommendation {
    @Autowired
    private UsersRepo userRepository;

    @Autowired
    private TestSubmissionRepository testSubmissionRepository;

    @Autowired
    private UserFeedbackRepo userFeedbackRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, Object> getRecommendationsForUser(int userId) {

        // 1. Récupérer l'utilisateur connecté
        OurUsers user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // 2. Récupérer le dernier test soumis par l'utilisateur
        double quizScore = testSubmissionRepository.findTopByUserIdOrderBySubmittedAtDesc(userId)
                .map(submission -> submission.getScore())
                .orElse(0.0);

        // 3. Récupérer les feedbacks de l'utilisateur
        List<UserFeedback> feedbacks = userFeedbackRepository.findByUserId(userId);

        // Construire la liste des feedbacks au format attendu par l'API FastAPI
        List<Map<String, Object>> feedbackList = new ArrayList<>();
        for (UserFeedback feedback : feedbacks) {
            Map<String, Object> map = new HashMap<>();
            map.put("trainingName", feedback.getTraining().getTitle());
            map.put("rating", feedback.getRating());
            map.put("comment", feedback.getComment());
            feedbackList.add(map);
        }

        // 4. Créer la requête JSON à envoyer à l'API FastAPI
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("quiz_score", quizScore);
        requestBody.put("feedbacks", feedbackList);

        // 5. Configurer les headers pour la requête POST
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // 6. Appeler l'API FastAPI
        String url = "http://localhost:8002/recommend/existing-user";
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        // 7. Récupérer les recommandations et ajouter les informations de l'utilisateur
        Map<String, Object> responseBody = response.getBody();
        Map<String, Object> result = new HashMap<>();

        if (responseBody != null) {
            // Ajouter les recommandations dans la réponse
            result.put("recommendations", responseBody.get("recommendations"));
            // Ajouter l'email de l'utilisateur et le score
            result.put("user", user.getEmail());  // Supposons que l'email de l'utilisateur est dans user.getEmail()
            result.put("score", quizScore);
        }

        return result;
    }
}

