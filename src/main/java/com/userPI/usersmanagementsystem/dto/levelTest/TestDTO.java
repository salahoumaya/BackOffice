package com.userPI.usersmanagementsystem.dto.levelTest;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class TestDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime scheduledAt;
    private String image;


    private int duration;


    private Float score;

    private List<QuestionDTO> questions;

    public TestDTO(Long id, String image, String title, String description, LocalDateTime scheduledAt, int duration, Float score, List<QuestionDTO> questions) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.scheduledAt = scheduledAt;
        this.duration = duration;
        this.score = score;
        this.questions = questions;
    }




}
