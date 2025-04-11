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
public class TestResultDto {
    private String testTitle;
    private double score;
    private long timeSpentSeconds;
    private List<QuestionResultDto> questionResults;


}
