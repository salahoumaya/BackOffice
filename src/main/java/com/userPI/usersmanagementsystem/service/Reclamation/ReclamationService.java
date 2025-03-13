package com.userPI.usersmanagementsystem.service.Reclamation;

import com.userPI.usersmanagementsystem.entity.Event;
import com.userPI.usersmanagementsystem.entity.Reclamation;
import com.userPI.usersmanagementsystem.entity.SujetPfe;
import com.userPI.usersmanagementsystem.entity.Training;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReclamationService implements IReclamationService {
    @Autowired
     ReclamationRepository reclamationRepository;
    @Autowired
     EventRepository eventRepository;
    @Autowired
    TrainingRepo trainingRepository;
    @Autowired
     SujetPfeRepo sujetPfeRepository;
    @Autowired
    UsersRepo usersRepo;

    // 🔹 Créer une réclamation
    public Reclamation createReclamation(Integer userId, Long eventId, Integer trainingId, Integer sujetPfeId, String title, String description) {
        OurUsers user = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Reclamation reclamation = new Reclamation();
        reclamation.setUser(user);
        reclamation.setTitle(title);
        reclamation.setDescription(description);

        if (eventId != null) {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
            reclamation.setEvent(event);
        }

        if (trainingId != null) {
            Training training = trainingRepository.findById(trainingId)
                    .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
            reclamation.setTraining(training);
        }

        if (sujetPfeId != null) {
            SujetPfe sujetPfe = sujetPfeRepository.findById(sujetPfeId)
                    .orElseThrow(() -> new RuntimeException("Sujet PFE non trouvé"));
            reclamation.setSujetPfe(sujetPfe);
        }

        return reclamationRepository.save(reclamation);
    }

    @Override
    public Reclamation getReclamationById(Integer id) {
        return null;
    }

    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    @Override
    public List<Reclamation> getReclamationsByTrainingId(Integer trainingId) {
        return List.of();
    }

    @Override
    public List<Reclamation> getReclamationsByUserId(Integer userId) {
        return List.of();
    }

    @Override
    public List<Reclamation> getReclamationsByEventId(Integer eventId) {
        return List.of();
    }

    public Reclamation getReclamationById(Long id) {
        return reclamationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée"));
    }

    public Reclamation updateReclamation(Long id, String title, String description) {
        Reclamation reclamation = getReclamationById(id);
        reclamation.setTitle(title);
        reclamation.setDescription(description);
        return reclamationRepository.save(reclamation);
    }

    public void deleteReclamation(Long id) {
        reclamationRepository.deleteById(id);
    }

    //public Reclamation validateReclamation(Long id, boolean approved, String rejectionReason);

    //public void sendReclamationNotification(OurUsers user, Reclamation reclamation);


    //public List<ReclamationHistory> getReclamationHistory(Long reclamationId);


   // public List<Reclamation> getPendingUrgentReclamations();

    //public ReclamationStatisticsDTO getReclamationStatistics();





}
