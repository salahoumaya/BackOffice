package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.entity.Planning;
import com.userPI.usersmanagementsystem.entity.PlanningDay;
import com.userPI.usersmanagementsystem.entity.Training;
import com.userPI.usersmanagementsystem.repository.PlanningRepository;
import com.userPI.usersmanagementsystem.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanningService implements IPlanningService {

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    @Override
    public Planning createPlanning(Planning planning, int trainingId) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new RuntimeException("Training not found!"));

        planning.setTraining(training);


        if (planning.getPlanningDays() != null) {
            for (PlanningDay day : planning.getPlanningDays()) {
                day.setId(0);
            }
        }

        return planningRepository.save(planning);
    }

    @Override
    public Optional<Planning> getPlanningById(int id) {
        return planningRepository.findById(id);
    }

    @Override
    public List<Planning> getAllPlannings() {
        return planningRepository.findAll();
    }

    @Override
    public Planning updatePlanning(int id, Planning updatedPlanning) {
        return planningRepository.findById(id).map(planning -> {
            planning.setStartDate(updatedPlanning.getStartDate());
            planning.setEndDate(updatedPlanning.getEndDate());
            planning.setPlanningType(updatedPlanning.getPlanningType());

            // Remove old planning days and replace with new ones
            planning.getPlanningDays().clear();
            planning.getPlanningDays().addAll(updatedPlanning.getPlanningDays());

            return planningRepository.save(planning);
        }).orElseThrow(() -> new RuntimeException("Planning not found!"));
    }

    @Override
    public void deletePlanning(int id) {
        planningRepository.deleteById(id);
    }
}
