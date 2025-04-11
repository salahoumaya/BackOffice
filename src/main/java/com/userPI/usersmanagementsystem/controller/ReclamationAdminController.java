package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.entity.Event;
import com.userPI.usersmanagementsystem.entity.Reclamation;
import com.userPI.usersmanagementsystem.entity.SujetPfe;
import com.userPI.usersmanagementsystem.entity.Training;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.repository.EventRepository;
import com.userPI.usersmanagementsystem.repository.SujetPfeRepo;
import com.userPI.usersmanagementsystem.repository.TrainingRepo;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import com.userPI.usersmanagementsystem.service.Event.EventService;
import com.userPI.usersmanagementsystem.service.Reclamation.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/reclamations")
//@PreAuthorize("hasRole('ADMIN')")
public class ReclamationAdminController {
    @Autowired
    private ReclamationService reclamationService;

    // ✅ 1. Liste de toutes les réclamations
    @GetMapping
    public ResponseEntity<List<Reclamation>> getAllReclamations() {
        return ResponseEntity.ok(reclamationService.getAllReclamations());
    }

    // ✅ 2. Voir une réclamation spécifique
    @GetMapping("/{id}")
    public ResponseEntity<Reclamation> getReclamationById(@PathVariable Long id) {
        return ResponseEntity.ok(reclamationService.getReclamationById(id));
    }

    // ✅ 3. Modifier statut + réponse (pour admin uniquement)
    @PutMapping("/{id}/update")
    public ResponseEntity<Reclamation> adminUpdateReclamation(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) String responseMessage) {

        return ResponseEntity.ok(reclamationService.updateStatusAndResponse(id, status, responseMessage));
    }

    // ✅ 4. Supprimer une réclamation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReclamation(@PathVariable Long id) {
        reclamationService.deleteReclamation(id);
        return ResponseEntity.noContent().build();
    }
}