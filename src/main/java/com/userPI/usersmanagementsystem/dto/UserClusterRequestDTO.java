package com.userPI.usersmanagementsystem.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserClusterRequestDTO {
        private Double quizScores;
        private Integer userSatisfaction;
        private Double quizPerformance;
        private Double sessionDuration;
        private Double sessionsPerWeek;
        private Double averageSessionDuration;
        private Double activitySpan;
}
