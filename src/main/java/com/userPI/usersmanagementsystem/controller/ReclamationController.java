package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.entity.Event;
import com.userPI.usersmanagementsystem.entity.Reclamation;
import com.userPI.usersmanagementsystem.entity.SujetPfe;
import com.userPI.usersmanagementsystem.entity.Training;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.repository.*;
import com.userPI.usersmanagementsystem.service.Event.EventService;
import com.userPI.usersmanagementsystem.service.Reclamation.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/reclamations")
//@PreAuthorize("hasRole('ADMIN')")
public class ReclamationController {
    @Autowired
    private ReclamationService reclamationService;

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private SujetPfeRepo sujetPfeRepo;

    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ReclamationRepository reclamationRepository;

    @PostMapping
    public ResponseEntity<?> createReclamation(
            @RequestBody Map<String, Object> requestData,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails) {

        try {
            String userEmail = userDetails.getUsername();
            OurUsers user = usersRepo.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

            if (!validateRequestData(requestData)) {
                return ResponseEntity.badRequest().body("Tous les champs sont obligatoires");
            }

            String type = requestData.get("type").toString().trim().toUpperCase();
            String title = requestData.get("title").toString().trim();
            String description = requestData.get("description").toString().trim();

            Object targetObj = requestData.get("targetId");
            if (targetObj == null) {
                return ResponseEntity.badRequest().body("targetId est requis");
            }

            // --- Type & ID ---
            Long eventId = null;
            Integer trainingId = null;
            Integer sujetpfeId = null;

            switch (type) {
                case "EVENT":
                    // ✅ utilise longValue()
                    eventId = ((Number) targetObj).longValue();
                    break;

                case "TRAINING":
                    trainingId = ((Number) targetObj).intValue();
                    break;

                case "SUJET_PFE":
                    sujetpfeId = ((Number) targetObj).intValue();
                    break;

                default:
                    return ResponseEntity.badRequest().body("Type de réclamation invalide");
            }

            Reclamation reclamation = reclamationService.createReclamation(
                    user, type, eventId, trainingId, sujetpfeId, title, description);

            return ResponseEntity.ok(reclamation);

        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body("targetId doit être un nombre (Number)");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur serveur: " + e.getMessage());
        }
    }


    private boolean validateRequestData(Map<String, Object> requestData) {
        return requestData.containsKey("type") &&
                requestData.containsKey("targetId") &&
                requestData.containsKey("title") &&
                requestData.containsKey("description") &&
                !requestData.get("title").toString().isBlank() &&
                !requestData.get("description").toString().isBlank();
    }


    @GetMapping("/events")
  public List<Event>getAllEvents() {
        return eventRepository.findAll();
    }
    // TrainingController
    @GetMapping("/trainings")
    public List<Training> getAllTrainings() {
        return trainingRepo.findAll();
    }

    // SujetPfeController
    @GetMapping("/sujets-pfe")
    public List<SujetPfe> getAllSujetsPfe() {
        return sujetPfeRepo.findAll();
    }



    @GetMapping
    public ResponseEntity<List<Reclamation>> getAllReclamations() {
        return ResponseEntity.ok(reclamationService.getAllReclamations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamation> getReclamationById(@PathVariable Long id) {
        return ResponseEntity.ok(reclamationService.getReclamationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reclamation> updateReclamation(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description) {
        return ResponseEntity.ok(reclamationService.updateReclamation(id, title, description));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Reclamation> updateReclamationStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(reclamationService.updateReclamationStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReclamation(@PathVariable Long id) {
        reclamationService.deleteReclamation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<Reclamation>> getMyReclamations(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails) {
        OurUsers user = usersRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        return ResponseEntity.ok(reclamationService.getReclamationsByUserId(user.getId()));
    }

    @PutMapping("/{id}/mark-as-read")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        Reclamation reclamation = reclamationService.getReclamationById(id);
        reclamation.setRead(true);
        reclamationRepository.save(reclamation);
        return ResponseEntity.ok().build();
    }

}