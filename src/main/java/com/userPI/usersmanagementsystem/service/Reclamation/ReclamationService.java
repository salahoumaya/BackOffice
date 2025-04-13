package com.userPI.usersmanagementsystem.service.Reclamation;

import com.userPI.usersmanagementsystem.entity.*;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReclamationService implements IReclamationService {

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TrainingRepo trainingRepository;

    @Autowired
    private SujetPfeRepo sujetPfeRepository;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private ReclamationRuleEngine ruleEngine;

    public Reclamation createReclamation(OurUsers user, String type, Long eventId, Integer trainingId, Integer sujetpfeId, String title, String description) {
        Reclamation reclamation = new Reclamation();
        reclamation.setUser(user);
        reclamation.setTitle(title);
        reclamation.setDescription(description);
        reclamation.setType(type.toUpperCase());

        // Affecter l'entité ciblée et nom
        switch (reclamation.getType()) {
            case "EVENT":
                if (eventId == null) throw new IllegalArgumentException("L'ID d'événement est requis");
                Event event = eventRepository.findById(eventId)
                        .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
                reclamation.setEvent(event);
                reclamation.setTargetName(event.getTitle());
                break;
            case "TRAINING":
                if (trainingId == null) throw new IllegalArgumentException("L'ID de formation est requis");
                Training training = trainingRepository.findById(trainingId)
                        .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
                reclamation.setTraining(training);
                reclamation.setTargetName(training.getTitle());
                break;
            case "SUJET_PFE":
                if (sujetpfeId == null) throw new IllegalArgumentException("L'ID de sujet PFE est requis");
                SujetPfe sujetPfe = sujetPfeRepository.findById(sujetpfeId)
                        .orElseThrow(() -> new RuntimeException("Sujet PFE non trouvé"));
                reclamation.setSujetPfe(sujetPfe);
                reclamation.setTargetName(sujetPfe.getTitre());
                break;
            default:
                throw new IllegalArgumentException("Type de réclamation inconnu: " + type);
        }

        // 💡 Appliquer la règle de traitement automatique
        String autoStatus = ruleEngine.determineStatusFromRules(type, description);
        reclamation.setStatus(autoStatus);
        if (!"OPEN".equals(autoStatus)) {
            reclamation.setAutoProcessed(true); }


        return reclamationRepository.save(reclamation);
    }




    public Reclamation getReclamationById(Long id) {
        return reclamationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
    }


    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }


    public List<Reclamation> getReclamationsByUserId(Integer userId) {
        OurUsers user = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
        return reclamationRepository.findByUser(user);
    }


    public Reclamation updateReclamation(Long id, String title, String description) {
        Reclamation reclamation = getReclamationById(id);
        reclamation.setTitle(title);
        reclamation.setDescription(description);
        return reclamationRepository.save(reclamation);
    }


    public Reclamation updateReclamationStatus(Long id, String status) {
        Reclamation reclamation = getReclamationById(id);
        reclamation.updateStatus(status);
        return reclamationRepository.save(reclamation);
    }


    public void deleteReclamation(Long id) {

        reclamationRepository.deleteById(id);
    }
    public Reclamation updateStatusAndResponse(Long id, String status, String responseMessage) {
        Reclamation reclamation = getReclamationById(id);
        reclamation.updateStatus(status);
        reclamation.setResponseMessage(responseMessage);
        return reclamationRepository.save(reclamation);
    }
    public List<Reclamation> getUnreadReclamationsByUserId(Integer userId) {
        OurUsers user = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        return reclamationRepository.findByUserAndStatusNotAndReadIsFalse(user, "OPEN");
    }

    public Map<String, Long> getCountByType() {
        List<Object[]> raw = reclamationRepository.countByType();
        return raw.stream().collect(Collectors.toMap(
                obj -> (String) obj[0],
                obj -> (Long) obj[1]
        ));
    }

    public Map<String, Long> getCountByStatus() {
        List<Object[]> raw = reclamationRepository.countByStatus();
        return raw.stream().collect(Collectors.toMap(
                obj -> (String) obj[0],
                obj -> (Long) obj[1]
        ));
    }

    public Map<String, Long> getCountByMonth() {
        List<Object[]> raw = reclamationRepository.countByMonth();
        return raw.stream().collect(Collectors.toMap(
                obj -> "Mois " + obj[0].toString(),
                obj -> (Long) obj[1]
        ));
    }







}