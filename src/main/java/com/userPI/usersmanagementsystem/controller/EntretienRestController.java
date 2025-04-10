package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.entity.Entretien;
import com.userPI.usersmanagementsystem.service.IEntretienService;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/entretiens")
public class EntretienRestController {

    IEntretienService entretienService;

    // http://localhost:8089/exam/entretien/retrieve-all-entretiens

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
