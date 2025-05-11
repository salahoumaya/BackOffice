package com.userPI.usersmanagementsystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserClusterViewDTO {
    private Integer userId;
    private String email;
    private Integer cluster;
    private String label;
    private String commentaire;
    private Integer userSatisfaction;
    private Double quizScore;
    private Double quizPerformance;
    private Double sessionDuration;
    private Double sessionsPerWeek;
    private Double averageSessionDuration;
    private Integer activitySpan;
}
