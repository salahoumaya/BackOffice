package tn.esprit.formation.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.formation.Entities.Examen;
import tn.esprit.formation.Service.ExamenService;

import java.util.List;

@RestController
@RequestMapping("/examens")
public class ExamenController {
    @Autowired
    private ExamenService examenService;

    @PostMapping
    public ResponseEntity<Examen> createExamen(@RequestBody Examen examen) {
        return new ResponseEntity<>(examenService.createExamen(examen), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Examen>> getAllExamens() {
        return new ResponseEntity<>(examenService.getAllExamens(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Examen> getExamenById(@PathVariable Long id) {
        return examenService.getExamenById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamen(@PathVariable Long id) {
        examenService.deleteExamen(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Examen> updateExamen(@PathVariable Long id, @RequestBody Examen examen) {
        return ResponseEntity.ok(examenService.updateExamen(id, examen));
    }

}
