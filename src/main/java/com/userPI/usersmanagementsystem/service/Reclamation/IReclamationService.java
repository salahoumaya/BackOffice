package com.userPI.usersmanagementsystem.service.Reclamation;

import com.userPI.usersmanagementsystem.entity.Reclamation;

import java.util.List;

public interface IReclamationService {
    public Reclamation createReclamation(Integer userId, Long eventId, Integer trainingId, Integer sujetPfeId, String title, String description);
    public Reclamation getReclamationById(Integer id);
    public List<Reclamation> getAllReclamations();
    public List<Reclamation> getReclamationsByTrainingId(Integer trainingId);
    public List<Reclamation> getReclamationsByUserId(Integer userId);
    public List<Reclamation> getReclamationsByEventId(Integer eventId);




}
