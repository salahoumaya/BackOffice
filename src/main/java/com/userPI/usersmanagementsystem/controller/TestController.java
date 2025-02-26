package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.dto.TestDTO;
import com.userPI.usersmanagementsystem.service.TestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/tests")
@PreAuthorize("hasRole('ADMIN')") // 🔒 Sécurisation des endpoints admin
public class TestController {

    @Autowired
    private TestService testService;

    // ✅ Récupérer tous les tests
    @GetMapping
    public ResponseEntity<List<TestDTO>> getAllTests() {
        List<TestDTO> tests = testService.getAllTests();
        return ResponseEntity.ok(tests);
    }

    // ✅ Récupérer un test par ID
    @GetMapping("/{id}")
    public ResponseEntity<TestDTO> getTestById(@PathVariable Long id) {
        Optional<TestDTO> testDTO = testService.getTestById(id);
        return testDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Créer un test
    @PostMapping
    public ResponseEntity<TestDTO> createTest(@Valid @RequestBody TestDTO testDTO) {
        TestDTO createdTest = testService.createTest(testDTO);
        return ResponseEntity.ok(createdTest);
    }

    // ✅ Supprimer un test
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }
}
