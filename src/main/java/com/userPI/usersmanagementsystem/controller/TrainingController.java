package com.userPI.usersmanagementsystem.controller;
//import com.userPI.usersmanagementsystem.dto.dashModerator;
import com.userPI.usersmanagementsystem.dto.UserMoyenneResponse;
import com.userPI.usersmanagementsystem.dto.dashModerator;
import com.userPI.usersmanagementsystem.entity.Training;
import com.userPI.usersmanagementsystem.service.ITrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class TrainingController {
    private ITrainingService trainingService;

    @GetMapping("/public-training/getAllTraining")
    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @PostMapping("/moderator/addTraining")
    public Training addTraining(@Valid @RequestBody Training training) {
        return trainingService.addTraining(training);
    }

    @PostMapping("/moderator/addTrainingWithCourses")
    public Training addTrainingWithCourses( @Valid @RequestBody Training training){
        return trainingService.addTrainingWithCourses(training);
    }
    @DeleteMapping("/moderator/deleteTrainingWithCourses/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable int id) {
        trainingService.deleteTrainingWithCourses(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/moderator/ModifyTrainingAndCourse/{id}")
    public ResponseEntity<Training> updateTraining(@PathVariable int id,@Valid @RequestBody Training training) {
        try {
            return ResponseEntity.ok(trainingService.updateTrainingWithCourses(id, training));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/public-training/getTrainingById/{id}")
    public ResponseEntity<Training> getTrainingById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(trainingService.getTrainingById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/public-training/subscribe/{idTraining}")
    @PreAuthorize("hasRole('USER')")
    public Training addUserToTraining(@PathVariable int idTraining) {
        return trainingService.addUserToTraining(idTraining);
    }


    @GetMapping("/moderator/get-trainings")
    public List<Training> getTrainingsForTrainer() {
        return trainingService.getTrainingsForAuthenticatedTrainer();
    }

    @GetMapping("/public-training/student/get-trainings")
    public List<Training> getTrainingsForStudent() {
        return trainingService.getTrainingsForAuthenticatedStudent();
    }

    @GetMapping("/moderator/get-dashboard")
    public dashModerator getDashboard() {
        return trainingService.getDashboard();
    }
    @PostMapping("/{formationId}/assign/{userId}")
    public String assignUserToFormation(@PathVariable Integer formationId, @PathVariable Integer userId) {
        return trainingService.assignUserToFormation(userId, formationId);
    }
    @GetMapping("/buuser/{id}")
    public ResponseEntity<List<Training>> getbuuser(@PathVariable Integer id) {
        return ResponseEntity.ok(trainingService.getFormationsusers(id));
    }
    @GetMapping("/{id}/users-moyennes")
    public ResponseEntity<List<UserMoyenneResponse>> getUsersAndMoyennes(@PathVariable Long id) {
        return ResponseEntity.ok(trainingService.getUsersWithMoyenne(id));
    }
}
