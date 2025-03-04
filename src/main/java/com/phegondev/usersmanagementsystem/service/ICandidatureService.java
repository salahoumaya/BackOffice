package com.phegondev.usersmanagementsystem.service;

import com.phegondev.usersmanagementsystem.entity.Candidature;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface ICandidatureService {

    List<Candidature> retrieveAllCandidatures();
    Candidature retrieveCandidature(Long candidatureId);
    Candidature addCandidature(Candidature c);
    void removeCandidature(Long candidatureId);
    Candidature modifyCandidature(Candidature candidature);

    // Nouvelle méthode pour générer un PDF des candidatures
    ByteArrayOutputStream generateCandidaturePdf();

    // Autres méthodes commentées
    // List<Candidature> findCandidaturesBySpecialite(String specialite);
    // void importCV(Long candidatureId, String cv);
    // List<HistoryRecord> getCandidatureHistory(Long candidatureId);
    // Report generateReport(ReportCriteria criteria);
}