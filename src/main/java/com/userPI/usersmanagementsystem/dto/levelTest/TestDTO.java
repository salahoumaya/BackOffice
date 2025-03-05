package com.userPI.usersmanagementsystem.dto.levelTest;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime scheduledAt;


    private int duration;


    private Float score;

    private List<QuestionDTO> questions;
}
