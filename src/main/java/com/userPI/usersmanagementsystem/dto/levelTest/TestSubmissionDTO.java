package com.userPI.usersmanagementsystem.dto.levelTest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestSubmissionDTO {
    private Long userId;
    private String userName;
    private Long testId;
    private double score;
    private LocalDateTime submittedAt;
    private LocalDateTime startTime;
    private List<AnswerDTO> answers;




}
