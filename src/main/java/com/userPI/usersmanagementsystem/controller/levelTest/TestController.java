package com.userPI.usersmanagementsystem.controller.levelTest;

import com.userPI.usersmanagementsystem.dto.levelTest.TestDTO;
import com.userPI.usersmanagementsystem.dto.levelTest.TestStatisticsDTO;
import com.userPI.usersmanagementsystem.dto.levelTest.TestSubmissionDTO;
import com.userPI.usersmanagementsystem.service.TesLevel.ITestService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/tests")
@PreAuthorize("hasRole('ADMIN')")
public class TestController {

    @Autowired
    ITestService testService;


    @GetMapping
    public ResponseEntity<List<TestDTO>> getAllTests() {
        List<TestDTO> tests = testService.getAllTests();
        return ResponseEntity.ok(tests);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TestDTO> getTestById(@PathVariable Long id) {
        Optional<TestDTO> testDTO = testService.getTestById(id);
        return testDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TestDTO> createTest(@Valid @RequestBody TestDTO testDTO) {
        System.out.println("🛠 Requête reçue pour création de test : " + testDTO);
        TestDTO createdTest = testService.createTest(testDTO);
        return ResponseEntity.ok(createdTest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{testId}/submissions")
    public ResponseEntity<List<TestSubmissionDTO>> getTestSubmissions(@PathVariable Long testId) {
        List<TestSubmissionDTO> submissions = testService.getTestSubmissions(testId);
        return ResponseEntity.ok(submissions);
    }
    @GetMapping("/{testId}/statistics")
    public ResponseEntity<TestStatisticsDTO> getTestStatistics(@PathVariable Long testId) {
        return ResponseEntity.ok(testService.getTestStatistics(testId));
    }





}
