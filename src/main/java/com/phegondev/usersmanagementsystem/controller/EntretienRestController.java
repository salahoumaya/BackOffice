package com.phegondev.usersmanagementsystem.controller;

import com.phegondev.usersmanagementsystem.entity.Entretien;
import com.phegondev.usersmanagementsystem.service.IEntretienService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "Ce Web Service gère le CRUD pour une Entretien")
@RestController
@AllArgsConstructor
@RequestMapping("/entretiens")
public class EntretienRestController {

    IEntretienService entretienService;

    // http://localhost:8089/exam/entretien/retrieve-all-entretiens
    @Operation(description = "Ce web service permet de récupérer toutes les entretiens de la base de données")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/retrieve-all-entretiens")
    public List<Entretien> getEntretiens() {
        List<Entretien> listEntretiens = entretienService.retrieveAllEntretiens();
        return listEntretiens;
    }
    // http://localhost:8089/exam/entretien/retrieve-entretien/8
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/retrieve-entretien/{entretien-id}")
    public Entretien retrieveEntretien(@PathVariable("entretien-id") Long chId) {
        Entretien entretien = entretienService.retrieveEntretien(chId);
        return entretien;
    }

    // http://localhost:8089/exam/entretien/add-entretien
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/add-entretien")
    public Entretien addEntretien(@RequestBody Entretien c) {
        Entretien entretien = entretienService.addEntretien(c);
        return entretien;
    }

    // http://localhost:8089/exam/entretien/remove-entretien/{entretien-id}
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/remove-entretien/{entretien-id}")
    public void removeEntretien(@PathVariable("entretien-id") Long chId) {
        entretienService.removeEntretien(chId);
    }

    // http://localhost:8089/exam/entretien/modify-entretien
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/modify-entretien")
    public Entretien modifyEntretien(@RequestBody Entretien c) {
        Entretien entretien = entretienService.modifyEntretien(c);
        return entretien;
    }


}
