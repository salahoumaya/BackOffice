package tn.esprit.formation.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.formation.Entities.Diplome;
import tn.esprit.formation.Service.DiplomeService;

import java.util.List;

@RestController
@RequestMapping("/diplomes")
public class DiplomeController {
    @Autowired
    private DiplomeService diplomeService;

    // ✅ Créer un diplôme
    @PostMapping
    public ResponseEntity<Diplome> createDiplome(@RequestBody Diplome diplome) {
        return new ResponseEntity<>(diplomeService.createDiplome(diplome), HttpStatus.CREATED);
    }

    // ✅ Récupérer tous les diplômes
    @GetMapping
    public ResponseEntity<List<Diplome>> getAllDiplomes() {
        return new ResponseEntity<>(diplomeService.getAllDiplomes(), HttpStatus.OK);
    }

    // ✅ Récupérer un diplôme par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Diplome> getDiplomeById(@PathVariable Long id) {
        return diplomeService.getDiplomeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Supprimer un diplôme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiplome(@PathVariable Long id) {
        diplomeService.deleteDiplome(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Diplome> updateDiplome(@PathVariable Long id, @RequestBody Diplome diplome) {
        return ResponseEntity.ok(diplomeService.updateDiplome(id, diplome));
    }

}
