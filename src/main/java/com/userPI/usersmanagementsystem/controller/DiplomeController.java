package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.entity.Diplome;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.repository.DiplomeRepository;
import com.userPI.usersmanagementsystem.repository.ExamenRepository;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import com.userPI.usersmanagementsystem.service.DiplomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public ResponseEntity<Diplome> createDiplome(@RequestBody Diplome diplome, @RequestParam Integer idformation, @RequestParam Integer iduser) {
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
    @GetMapping("/users/Image/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path path = Paths.get("diplomes/" + filename);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(path);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}










