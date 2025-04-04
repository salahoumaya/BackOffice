package tn.esprit.tacheevaluation.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;
import tn.esprit.tacheevaluation.entity.Formation;
import tn.esprit.tacheevaluation.service.FormationService;

import java.util.List;

@RestController
@RequestMapping("/formations")
public class FormationController {
    @Autowired
    private FormationService formationService;

    @PostMapping
    public ResponseEntity<Formation> createFormation(@RequestBody Formation formation) {
        return new ResponseEntity<>(formationService.createFormation(formation), HttpStatus.CREATED);
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<List<Formation>> getAllFormations() {
        return new ResponseEntity<>(formationService.getAllFormations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Long id) {
        return formationService.getFormationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Formation> updateFormation(@PathVariable Long id, @RequestBody Formation formation) {
        return ResponseEntity.ok(formationService.updateFormation(id, formation));
    }
    @PostMapping("/{formationId}/assign/{userId}")
    public String assignUserToFormation(@PathVariable Long formationId, @PathVariable Integer userId) {
        return formationService.assignUserToFormation(userId, formationId);
    }
    @GetMapping("/buuser/{id}")
    public ResponseEntity<List<Formation>> getbuuser(@PathVariable Integer id) {
        return ResponseEntity.ok(formationService.getFormationsusers(id));
    }






}
