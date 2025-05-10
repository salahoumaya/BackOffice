package com.userPI.usersmanagementsystem.service.user;

import com.userPI.usersmanagementsystem.entity.Training;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.entity.user.UserFeedback;
import com.userPI.usersmanagementsystem.repository.TrainingRepository;
import com.userPI.usersmanagementsystem.repository.UserFeedbackRepo;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserFeedbackService {

    @Autowired
    UserFeedbackRepo feedbackRepository;
    @Autowired
     UsersRepo userRepository;
    @Autowired
     TrainingRepository trainingRepository;

    public UserFeedback addUserFeedback(Integer userId, Integer trainingId, Integer rating) {

        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new RuntimeException("Formation introuvable avec ID : " + trainingId));

        OurUsers user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec ID : " + userId));

        UserFeedback feedback = new UserFeedback();
        feedback.setUser(user);
        feedback.setTraining(training);
        feedback.setRating(rating);

        return feedbackRepository.save(feedback);
    }




}
