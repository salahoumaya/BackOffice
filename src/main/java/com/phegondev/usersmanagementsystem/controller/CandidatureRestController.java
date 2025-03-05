package com.phegondev.usersmanagementsystem.controller;

import com.phegondev.usersmanagementsystem.entity.Candidature;
import com.phegondev.usersmanagementsystem.service.ICandidatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "Ce Web Service gère le CRUD pour une Candidature")
@RestController
@AllArgsConstructor
@RequestMapping("/candidatures")
public class CandidatureRestController {

    private final ICandidatureService candidatureService;

    @Operation(description = "Ce web service permet de récupérer toutes les candidatures de la base de données")
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
}
