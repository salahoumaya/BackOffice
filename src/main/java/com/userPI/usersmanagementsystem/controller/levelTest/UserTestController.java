package com.userPI.usersmanagementsystem.controller.levelTest;

import com.userPI.usersmanagementsystem.dto.levelTest.TestDTO;
import com.userPI.usersmanagementsystem.dto.levelTest.TestSubmissionDTO;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import com.userPI.usersmanagementsystem.service.TesLevel.ITestService;
import com.userPI.usersmanagementsystem.service.TesLevel.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

}
