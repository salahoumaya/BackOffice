package com.userPI.usersmanagementsystem.service.TesLevel;



import com.userPI.usersmanagementsystem.dto.levelTest.TestDTO;
import com.userPI.usersmanagementsystem.dto.levelTest.TestStatisticsDTO;
import com.userPI.usersmanagementsystem.dto.levelTest.TestSubmissionDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ITestService {
    public List<TestDTO> getAllTests();
    public Optional<TestDTO> getTestById(Long id);
    public TestDTO createTest(TestDTO testDTO);
    public void deleteTest(Long id);
    public double evaluateAndSaveTest(Integer userId, TestSubmissionDTO submissionDTO);
    public List<TestSubmissionDTO> getTestSubmissions(Long testId);
    public TestStatisticsDTO getTestStatistics(Long testId);


    double getTestSuccessRate(Long testId);





    byte[] exportTestResultsToExcel(Long testId);



}
