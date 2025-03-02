package com.phegondev.usersmanagementsystem.service;


import com.phegondev.usersmanagementsystem.entity.Candidature;
import com.phegondev.usersmanagementsystem.repository.CandidatureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CandidatureServiceImpl implements ICandidatureService {

    CandidatureRepository candidatureRepository;

    //@Scheduled(cron = "0/15 * * * * *")
    // @Scheduled(fixedDelay = 10000) // 10000 millisecondes
    public List<Candidature> retrieveAllCandidatures() {
        List<Candidature> listC = candidatureRepository.findAll();
        log.info("nombre total des candidats : " + listC.size());
       for (Candidature c: listC) {
           log.info("candidat : " + c);
       }
        return listC;
    }

    public Candidature retrieveCandidature(Long candidatId) {
        Candidature c = candidatureRepository.findById(candidatId).get();
        return c;
    }

    public Candidature addCandidature(Candidature c) {
        Candidature candidat = candidatureRepository.save(c);
        return candidat;
    }

    public Candidature modifyCandidature(Candidature candidat) {
        Candidature c = candidatureRepository.save(candidat);
        return c;
    }

    public void removeCandidature(Long candidatId) {
        candidatureRepository.deleteById(candidatId);
    }

}
