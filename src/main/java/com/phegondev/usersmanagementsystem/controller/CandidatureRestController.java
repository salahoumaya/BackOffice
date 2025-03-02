package com.phegondev.usersmanagementsystem.controller;

import com.phegondev.usersmanagementsystem.entity.Candidature;
import com.phegondev.usersmanagementsystem.service.ICandidatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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
}

