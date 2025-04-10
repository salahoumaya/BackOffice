package com.userPI.usersmanagementsystem.controller.levelTest;

import com.userPI.usersmanagementsystem.dto.levelTest.TestDTO;
import com.userPI.usersmanagementsystem.dto.levelTest.TestExplanationDTO;
import com.userPI.usersmanagementsystem.dto.levelTest.TestResultDto;
import com.userPI.usersmanagementsystem.dto.levelTest.TestSubmissionDTO;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import com.userPI.usersmanagementsystem.service.TesLevel.ITestService;
import com.userPI.usersmanagementsystem.service.TesLevel.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user/tests")
@PreAuthorize("hasRole('USER')")

public class UserTestController {

    @Autowired
    ITestService testService;
    @Autowired
    UsersRepo userRepository;


    @GetMapping
    public ResponseEntity<List<TestDTO>> getAvailableTests() {
        List<TestDTO> tests = testService.getAllTests();
        return ResponseEntity.ok(tests);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TestDTO> getTestById(@PathVariable Long id) {
        Optional<TestDTO> testDTO = testService.getTestById(id);
        return testDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/submit")
    public ResponseEntity<Double> submitTest(@RequestBody TestSubmissionDTO submissionDTO, @AuthenticationPrincipal UserDetails userDetails) {
        OurUsers user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        double score = testService.evaluateAndSaveTest(user.getId(), submissionDTO);
        return ResponseEntity.ok(score);
    }

    @GetMapping("/{testId}/result")
    public ResponseEntity<TestResultDto> getUserTestResult( @PathVariable Long testId,
                                                            @AuthenticationPrincipal UserDetails userDetails) {
        OurUsers user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return ResponseEntity.ok(testService.getResultForUser(testId, user.getId()));
    }

    // TestController.java

    @PostMapping("/test/explain")
    public ResponseEntity<Map<String, String>> getLLMExplanation(@RequestBody TestExplanationDTO dto) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<TestExplanationDTO> request = new HttpEntity<>(dto, headers);
            String url = "http://localhost:8010/explain";

            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            // Construire une vraie réponse JSON
            Map<String, String> result = new HashMap<>();
            result.put("explanation", (String) response.getBody().get("explanation"));

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            // Retourner aussi une Map pour rester en JSON
            Map<String, String> error = new HashMap<>();
            error.put("explanation", "❌ Erreur FastAPI : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }





}
