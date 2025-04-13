package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.entity.Candidature;

import java.io.IOException;
import java.util.List;

public interface ICandidatureService {

    List<Candidature> retrieveAllCandidatures();
    Candidature retrieveCandidature(Long candidatureId);
    Candidature addCandidature(Candidature c);
    void removeCandidature(Long candidatureId);
    Candidature modifyCandidature(Candidature candidature);

    // 📌 Ajout de la méthode pour générer un PDF
    byte[] generateCandidaturePdf() throws IOException;

    // 📌 Ajout de la méthode pour envoyer un email de confirmation
    void sendConfirmationEmail(String email);
}
