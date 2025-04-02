package tn.esprit.tacheevaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tacheevaluation.entity.Diplome;
import tn.esprit.tacheevaluation.entity.Examen;
import tn.esprit.tacheevaluation.entity.OurUsers;
import tn.esprit.tacheevaluation.repository.DiplomeRepository;
import tn.esprit.tacheevaluation.repository.ExamenRepository;
import tn.esprit.tacheevaluation.repository.UsersRepo;
import tn.esprit.tacheevaluation.service.DiplomeService;




import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/diplomes")
public class DiplomeController {
    @Autowired
    private DiplomeService diplomeService;
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private ExamenRepository examenRepository;


    @Autowired
    private DiplomeRepository diplomeRepository;

    // ✅ Créer un diplôme
    @PostMapping
    public ResponseEntity<Diplome> createDiplome(@RequestBody Diplome diplome,@RequestParam Long idformation,@RequestParam Integer iduser) {
        return new ResponseEntity<>(diplomeService.createDiplome(diplome,idformation,iduser), HttpStatus.CREATED);
    }



    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
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






    @GetMapping("/generate/{diplomaId}/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> generateDiploma(@PathVariable Long diplomaId, @PathVariable Integer userId) {
        // Vérifier si le diplôme existe
        Diplome diplome = diplomeRepository.findById(diplomaId)
                .orElseThrow(() -> new RuntimeException("Diplôme non trouvé"));

        // Vérifier si l'utilisateur existe
        OurUsers user = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérifier si l'utilisateur est bien celui du diplôme
        if (!diplome.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body("Cet utilisateur n'est pas associé à ce diplôme !");
        }

        try {
            // 📄 Générer le diplôme PDF
            String filePath = diplomeService.generateDiploma(diplome, user);

            System.out.println("✅ Diplôme généré avec succès : " + filePath);
            return ResponseEntity.ok("Diplôme généré avec succès : " + filePath);
        } catch (Exception e) {  // <-- Capture toutes les exceptions
            e.printStackTrace();  // <-- Affiche l'erreur exacte dans la console
            return ResponseEntity.status(500).body("Erreur lors de la génération du diplôme : " + e.getMessage());
        }
    }

}










