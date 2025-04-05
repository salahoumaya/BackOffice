package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.entity.Planning;

import java.util.List;
import java.util.Optional;

public interface IPlanningService {
    Planning createPlanning(Planning planning, int trainingId);
    Optional<Planning> getPlanningById(int id);
    List<Planning> getAllPlannings();
    Planning updatePlanning(int id, Planning updatedPlanning);
    void deletePlanning(int id);
}
