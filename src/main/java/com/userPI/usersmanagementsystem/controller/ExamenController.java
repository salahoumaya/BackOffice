package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.entity.Examen;
import com.userPI.usersmanagementsystem.entity.ExamenParticipant;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.service.ExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<List<Examen>> getAllExamensfor(@RequestParam Integer id) {
        return ResponseEntity.ok(examenService.getAllExamensbyFormation(id));
    }
    //  Ajouter un examen (admin, modérateur)
    @PostMapping("/{idformations}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<Examen> addExamen(@PathVariable Integer idformations, @RequestBody Examen examen) {
        return ResponseEntity.ok(examenService.addExamen(examen,idformations));
    }
    @GetMapping("/get/{id}/{idformation}")
    public ResponseEntity<Double> getmoyenne(@PathVariable Long idformation,@PathVariable Integer id) {
        return ResponseEntity.ok(examenService.getMoyenne(idformation,id));
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


//    @PostMapping("/{examenId}/participer/{userId}")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<String> participerExamen(@PathVariable Long examenId, @PathVariable Integer userId) {
//        String message = examenService.participerExamen(examenId, userId);
//        return ResponseEntity.ok(message);
//    }
    @GetMapping("/buuser/{id}")
    public ResponseEntity<List<ExamenParticipant>> getbuuser(@PathVariable Integer id) {
        return ResponseEntity.ok(examenService.getallexamen(id));
    }
    @PostMapping("/{examenId}/assign/{userId}")
    public String assignUserToExamen(@PathVariable Long examenId, @PathVariable Integer userId) {
        return examenService.assignUserToExamen(examenId, userId);
    }
    @PostMapping("/{id}/calcul")
    public String calculerMoyenneFormation(@PathVariable Integer id) {
        return examenService.calculerEtEnregistrerMoyenneParUtilisateur(id);
    }
    @PostMapping("/{examenId}/note/{userId}")
    public String addnote(@PathVariable Long examenId, @PathVariable Double userId) {
        return examenService.updateUserToExamen(examenId, userId);
    }
    @GetMapping("/{examenId}/participants")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<List<ExamenParticipant>> getParticipantsByExamen(@PathVariable Long examenId) {
        List<ExamenParticipant> participants = examenService.getParticipantsByExamen(examenId);
        return ResponseEntity.ok(participants);
    }






}
