package com.userPI.usersmanagementsystem.service;


//import com.userPI.usersmanagementsystem.dto.dashModerator;
import com.userPI.usersmanagementsystem.dto.UserMoyenneResponse;
import com.userPI.usersmanagementsystem.dto.dashModerator;
import com.userPI.usersmanagementsystem.entity.Training;

import java.util.List;

public interface ITrainingService {
    public List<Training> getFormationsusers(Integer id);
    public String assignUserToFormation(Integer userId, Integer formationId);

    public List<Training> getAllTrainings();
    public Training getTrainingById(int id);
    public Training addTraining(Training training);
    public Training addTrainingWithCourses(Training training);
    public void deleteTrainingWithCourses(int id);
    public Training updateTrainingWithCourses(int id, Training newTrainingData);
    public Training addUserToTraining(int trainingId);
    List<Training> getTrainingsForAuthenticatedTrainer();
    List<Training> getTrainingsForAuthenticatedStudent();
    dashModerator getDashboard();


    public List<UserMoyenneResponse> getUsersWithMoyenne(Long formationId);

}
