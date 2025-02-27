package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.dto.TestDTO;
import com.userPI.usersmanagementsystem.dto.TestSubmissionDTO;
import com.userPI.usersmanagementsystem.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user/tests")
@PreAuthorize("hasRole('USER')") // 🔒 Accessible uniquement aux utilisateurs authentifiés
public class UserTestController {

    @Autowired
    private TestService testService;

    // ✅ Récupérer la liste des tests disponibles
    @GetMapping
    public ResponseEntity<List<TestDTO>> getAvailableTests() {
        List<TestDTO> tests = testService.getAllTests(); // 📌 Ici, on peut ajouter un filtre pour les tests actifs
        return ResponseEntity.ok(tests);
    }

    // ✅ Récupérer un test par ID pour répondre aux questions
    @GetMapping("/{id}")
    public ResponseEntity<TestDTO> getTestById(@PathVariable Long id) {
        Optional<TestDTO> testDTO = testService.getTestById(id);
        return testDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Soumettre un test et calculer le score
    @PostMapping("/submit")
    public ResponseEntity<Double> submitTest(@RequestBody TestSubmissionDTO submissionDTO) {
        double score = testService.evaluateTest(submissionDTO);
        return ResponseEntity.ok(score);
    }
}
