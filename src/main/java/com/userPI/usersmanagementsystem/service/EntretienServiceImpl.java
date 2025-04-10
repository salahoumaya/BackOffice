package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.entity.Entretien;
import com.userPI.usersmanagementsystem.repository.EntretienRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EntretienServiceImpl implements IEntretienService {

    EntretienRepository entretienRepository;

    //@Scheduled(cron = "0/15 * * * * *")
    // @Scheduled(fixedDelay = 10000) // 10000 millisecondes
    public List<Entretien> retrieveAllEntretiens() {
        List<Entretien> listC = entretienRepository.findAll();
        log.info("nombre total des candidats : " + listC.size());
        for (Entretien c: listC) {
            log.info("candidat : " + c);
        }
        return listC;
    }

    public Entretien retrieveEntretien(Long candidatId) {
        Entretien c = entretienRepository.findById(candidatId).get();
        return c;
    }

    public Entretien addEntretien(Entretien c) {
        Entretien candidat = entretienRepository.save(c);
        return candidat;
    }

    public Entretien modifyEntretien(Entretien candidat) {
        Entretien c = entretienRepository.save(candidat);
        return c;
    }

    public void removeEntretien(Long candidatId) {
        entretienRepository.deleteById(candidatId);
    }

}
