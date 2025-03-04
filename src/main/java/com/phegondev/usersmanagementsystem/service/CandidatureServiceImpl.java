package com.phegondev.usersmanagementsystem.service;

import com.phegondev.usersmanagementsystem.entity.Candidature;
import com.phegondev.usersmanagementsystem.repository.CandidatureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CandidatureServiceImpl implements ICandidatureService {

    private final CandidatureRepository candidatureRepository;

    public List<Candidature> retrieveAllCandidatures() {
        List<Candidature> listC = candidatureRepository.findAll();
        log.info("nombre total des candidats : " + listC.size());
        for (Candidature c : listC) {
            log.info("candidat : " + c);
        }
        return listC;
    }

    public Candidature retrieveCandidature(Long candidatId) {
        return candidatureRepository.findById(candidatId).orElse(null);
    }

    public Candidature addCandidature(Candidature c) {
        return candidatureRepository.save(c);
    }

    public Candidature modifyCandidature(Candidature candidat) {
        return candidatureRepository.save(candidat);
    }

    public void removeCandidature(Long candidatId) {
        candidatureRepository.deleteById(candidatId);
    }

    // Implémentation de la méthode pour générer le PDF
    @Override
    public ByteArrayOutputStream generateCandidaturePdf() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Récupérer toutes les candidatures
            List<Candidature> candidatures = retrieveAllCandidatures();

            // Ajouter le contenu des candidatures au PDF
            for (Candidature candidature : candidatures) {
                document.add(new Paragraph("Candidature ID: " + candidature.getCandidatId()));
                document.add(new Paragraph("Nom: " + candidature.getNom()));
                document.add(new Paragraph("Email: " + candidature.getEmail()));
                // Ajoutez d'autres détails de la candidature ici
                document.add(new Paragraph("\n"));
            }

            document.close();
        } catch (Exception e) {
            log.error("Erreur lors de la génération du PDF: ", e);
        }
        return outputStream;
    }
}