package com.phegondev.usersmanagementsystem.controller;

import com.phegondev.usersmanagementsystem.entity.Candidature;
import com.phegondev.usersmanagementsystem.service.ICandidatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Tag(name = "Ce Web Service gère le CRUD pour une Candidature")
@RestController
@AllArgsConstructor
@RequestMapping("/candidatures")
public class CandidatureRestController {

    ICandidatureService candidatureService;

    @Operation(description = "Ce web service permet de récupérer toutes les candidatures de la base de données")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/retrieve-all-candidatures")
    public List<Candidature> getCandidatures() {
        return candidatureService.retrieveAllCandidatures();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/retrieve-candidature/{candidature-id}")
    public Candidature retrieveCandidature(@PathVariable("candidature-id") Long chId) {
        return candidatureService.retrieveCandidature(chId);
    }

    // ✅ Restreindre POST aux admins
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/add-candidature")
    public Candidature addCandidature(@RequestBody Candidature c) {
        return candidatureService.addCandidature(c);
    }

    // ✅ Restreindre DELETE aux admins
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/remove-candidature/{candidature-id}")
    public void removeCandidature(@PathVariable("candidature-id") Long chId) {
        candidatureService.removeCandidature(chId);
    }

    // ✅ Restreindre PUT aux admins
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/modify-candidature")
    public Candidature modifyCandidature(@RequestBody Candidature c) {
        return candidatureService.modifyCandidature(c);
    }

    // Nouvelle méthode pour télécharger le PDF des candidatures
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/download-pdf")
    public void downloadPdf(HttpServletResponse response) {
        // Récupérer toutes les candidatures
        List<Candidature> candidatures = candidatureService.retrieveAllCandidatures();

        // Créer le PDF
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Ajouter le contenu des candidatures au PDF
            for (Candidature candidature : candidatures) {
                document.add(new Paragraph("Candidature ID: " + candidature.getCandidatId()));
                document.add(new Paragraph("Nom: " + candidature.getNom()));
                document.add(new Paragraph("Email: " + candidature.getEmail()));
                // Ajoutez d'autres détails de la candidature ici
                document.add(new Paragraph("\n"));
            }

            document.close();

            // Configurer la réponse pour le téléchargement
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=candidatures.pdf");
            response.setContentLength(outputStream.size());
            response.getOutputStream().write(outputStream.toByteArray());
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
    }
}