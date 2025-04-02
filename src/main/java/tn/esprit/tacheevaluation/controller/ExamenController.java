package tn.esprit.tacheevaluation.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tacheevaluation.dto.ReqRes;
import tn.esprit.tacheevaluation.entity.Examen;

import tn.esprit.tacheevaluation.entity.OurUsers;
import tn.esprit.tacheevaluation.service.ExamenService;



import java.util.List;

@RestController
@RequestMapping("/examens")
public class ExamenController {
    @Autowired
    private ExamenService examenService;


    // ✅ Voir tous les examens (étudiants, admin, modérateur)
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<List<Examen>> getAllExamens() {
        return ResponseEntity.ok(examenService.getAllExamens());
    }
    @GetMapping("users")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<List<OurUsers>> getAlluser() {
        return ResponseEntity.ok(examenService.getAllUsers());
    }
    @GetMapping("byformation")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<List<Examen>> getAllExamensfor(@RequestParam Long id) {
        return ResponseEntity.ok(examenService.getAllExamensbyFormation(id));
    }
    //  Ajouter un examen (admin, modérateur)
    @PostMapping("/{userId}/{idformations}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<Examen> addExamen(@PathVariable Integer userId,@PathVariable Long idformations, @RequestBody Examen examen) {
        return ResponseEntity.ok(examenService.addExamen(examen, userId,idformations));
    }

    // Modifier un examen (admin, modérateur)
    @PutMapping("/{id}") //id examen
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<Examen> updateExamen(@PathVariable Long id, @RequestBody Examen examen) {
        return ResponseEntity.ok(examenService.updateExamen(id, examen));
    }

    // ✅ Supprimer un examen (admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteExamen(@PathVariable Long id) {
        String message = examenService.deleteExamen(id);
        return ResponseEntity.ok(message);
    }


    @PostMapping("/{examenId}/participer/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> participerExamen(@PathVariable Long examenId, @PathVariable Integer userId) {
        String message = examenService.participerExamen(examenId, userId);
        return ResponseEntity.ok(message);
    }
    @GetMapping("/{examenId}/participants")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<List<String>> getParticipantsByExamen(@PathVariable Long examenId) {
        List<String> participants = examenService.getParticipantsByExamen(examenId);
        return ResponseEntity.ok(participants);
    }






}
