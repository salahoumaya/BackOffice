package com.userPI.usersmanagementsystem.service;



import com.userPI.usersmanagementsystem.dto.TestDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ITestService {
    public List<TestDTO> getAllTests();
    public Optional<TestDTO> getTestById(Long id);
    public TestDTO createTest(TestDTO testDTO);
    public void deleteTest(Long id);


    double getTestSuccessRate(Long testId);



    boolean isTestTimeExpired(Long testId, Long studentId, LocalDateTime startTime);

    byte[] exportTestResultsToExcel(Long testId);



}
