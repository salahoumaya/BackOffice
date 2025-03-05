package com.userPI.usersmanagementsystem.dto.levelTest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestStatisticsDTO {
    private Long testId;
    private String testName;
    private double averageScore;
    private int totalParticipants;
    private double passRate;
    private List<QuestionStatisticsDTO> difficultQuestions;
    private BestTestDTO bestTest;



}
