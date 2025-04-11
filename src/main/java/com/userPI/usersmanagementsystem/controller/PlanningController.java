package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.entity.Planning;
import com.userPI.usersmanagementsystem.service.IPlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class PlanningController {

    @Autowired
    private IPlanningService planningService;

    @PostMapping("/moderator/addPlanning/{trainingId}")
    public ResponseEntity<Planning> createPlanning(@PathVariable int trainingId, @Valid @RequestBody Planning planning) {
        return ResponseEntity.ok(planningService.createPlanning(planning, trainingId));
    }

    @GetMapping("/public-training/plannings")
    public ResponseEntity<List<Planning>> getAllPlannings() {
        return ResponseEntity.ok(planningService.getAllPlannings());
    }

    @GetMapping("/public-training/planning/{id}")
    public ResponseEntity<Planning> getPlanningById(@PathVariable int id) {
        Optional<Planning> planning = planningService.getPlanningById(id);
        return planning.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/moderator/planning/{id}")
    public ResponseEntity<Planning> updatePlanning(@PathVariable int id,@Valid @RequestBody Planning updatedPlanning) {
        return ResponseEntity.ok(planningService.updatePlanning(id, updatedPlanning));
    }

    @DeleteMapping("/moderator/planning/{id}")
    public ResponseEntity<Void> deletePlanning(@PathVariable int id) {
        planningService.deletePlanning(id);
        return ResponseEntity.noContent().build();
    }
}
