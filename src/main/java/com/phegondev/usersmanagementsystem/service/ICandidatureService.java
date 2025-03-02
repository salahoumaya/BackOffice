package com.phegondev.usersmanagementsystem.service;



import com.phegondev.usersmanagementsystem.entity.Candidature;

import java.util.List;

public interface ICandidatureService {

    public List<Candidature> retrieveAllCandidatures();
    public Candidature retrieveCandidature(Long candidatureId);
    public Candidature addCandidature(Candidature c);
    public void removeCandidature(Long candidatureId);
    public Candidature modifyCandidature(Candidature candidature);

    //List<Candidature> findCandidaturesBySpecialite(String specialite);
    //void importCV(Long candidatureId, String cv);
    //List<HistoryRecord> getCandidatureHistory(Long candidatureId);
    //Report generateReport(ReportCriteria criteria);


    // Here we will add if any method calling keywords or JPQL


}
