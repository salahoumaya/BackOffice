package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.entity.Candidature;
import com.userPI.usersmanagementsystem.service.ICandidatureService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/candidatures")
public class CandidatureRestController {

    private final ICandidatureService candidatureService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/retrieve-all-candidatures")
    public List<Candidature> getCandidatures() {
        return candidatureService.retrieveAllCandidatures();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/retrieve-candidature/{candidature-id}")
    public Candidature retrieveCandidature(@PathVariable("candidature-id") Long candidatureId) {
        return candidatureService.retrieveCandidature(candidatureId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/add-candidature")
    public Candidature addCandidature(@RequestBody Candidature candidature) {
        return candidatureService.addCandidature(candidature);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/remove-candidature/{candidature-id}")
    public void removeCandidature(@PathVariable("candidature-id") Long candidatureId) {
        candidatureService.removeCandidature(candidatureId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/modify-candidature")
    public Candidature modifyCandidature(@RequestBody Candidature candidature) {
        return candidatureService.modifyCandidature(candidature);
    }

    // ✅ Nouvelle méthode pour télécharger le PDF
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/download-pdf")
    public ResponseEntity<byte[]> downloadPdf() throws IOException {
        byte[] pdfData = candidatureService.generateCandidaturePdf();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=candidatures.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfData);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/send-confirmation-email")
    public ResponseEntity<String> sendConfirmationEmail(@RequestParam String email) {
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("L'email ne peut pas être vide.");
        }

        try {
            candidatureService.sendConfirmationEmail(email);
            return ResponseEntity.ok("Email de confirmation envoyé à " + email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }
    @GetMapping("/search")
    public List<Candidature> searchCandidatures(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String prenom,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String specialite,
            @RequestParam(required = false) String statut
    ) {
        return candidatureService.retrieveAllCandidatures().stream()
                .filter(c -> nom == null || (c.getNom() != null && c.getNom().toLowerCase().contains(nom.toLowerCase())))
                .filter(c -> prenom == null || (c.getPrenom() != null && c.getPrenom().toLowerCase().contains(prenom.toLowerCase())))
                .filter(c -> email == null || (c.getEmail() != null && c.getEmail().toLowerCase().contains(email.toLowerCase())))
                .filter(c -> specialite == null || (c.getSpecialite() != null && c.getSpecialite().toLowerCase().contains(specialite.toLowerCase())))
                .filter(c -> statut == null || (c.getStatut() != null && c.getStatut().name().equalsIgnoreCase(statut)))
                .toList();
    }
    @GetMapping("/sort")
    public List<Candidature> sortCandidatures(
            @RequestParam(defaultValue = "nom") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        return candidatureService.retrieveAllCandidatures().stream()
                .sorted((c1, c2) -> {
                    int result = 0;

                    switch (sortBy.toLowerCase()) {
                        case "prenom" -> result = c1.getPrenom().compareToIgnoreCase(c2.getPrenom());
                        case "email" -> result = c1.getEmail().compareToIgnoreCase(c2.getEmail());
                        case "nbr_exp" -> result = Long.compare(c1.getNbr_exp(), c2.getNbr_exp());
                        case "specialite" -> result = c1.getSpecialite().compareToIgnoreCase(c2.getSpecialite());
                        case "statut" -> result = c1.getStatut().name().compareToIgnoreCase(c2.getStatut().name());

                    }

                    return "desc".equalsIgnoreCase(order) ? -result : result;
                })
                .toList();
    }

}