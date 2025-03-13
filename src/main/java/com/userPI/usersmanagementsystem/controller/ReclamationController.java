package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.entity.Reclamation;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import com.userPI.usersmanagementsystem.service.Reclamation.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    UsersRepo usersRepo;

@PostMapping
public ResponseEntity<Reclamation> createReclamation(
        @RequestBody Map<String, Object> requestData,
        @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails) {


    String userEmail = userDetails.getUsername();
    OurUsers user = usersRepo.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));


    Long eventId = requestData.get("eventId") != null ? Long.valueOf(requestData.get("eventId").toString()) : null;
    Integer trainingId = requestData.get("trainingId") != null ? Integer.valueOf(requestData.get("trainingId").toString()) : null;
    Integer sujetPfeId = requestData.get("sujetPfeId") != null ? Integer.valueOf(requestData.get("sujetPfeId").toString()) : null;
    String title = requestData.get("title").toString();
    String description = requestData.get("description").toString();

    // ✅ Appel du service en utilisant l'ID du User récupéré automatiquement
    return ResponseEntity.ok(reclamationService.createReclamation(user.getId().intValue(), eventId, trainingId, sujetPfeId, title, description));
}



    // ✅ Lire toutes les réclamations
    @GetMapping
    public ResponseEntity<List<Reclamation>> getAllReclamations() {
        return ResponseEntity.ok(reclamationService.getAllReclamations());
    }

    // ✅ Lire une réclamation par ID
    @GetMapping("/{id}")
    public ResponseEntity<Reclamation> getReclamationById(@PathVariable Long id) {
        return ResponseEntity.ok(reclamationService.getReclamationById(id));
    }

    // ✅ Modifier une réclamation
    @PutMapping("/{id}")
    public ResponseEntity<Reclamation> updateReclamation(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description) {

        return ResponseEntity.ok(reclamationService.updateReclamation(id, title, description));
    }

    // ✅ Supprimer une réclamation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReclamation(@PathVariable Long id) {
        reclamationService.deleteReclamation(id);
        return ResponseEntity.noContent().build();
    }
}
