package com.userPI.usersmanagementsystem.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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


    private int duration; // Doit être un `Integer`, pas `int` pour éviter les erreurs


    private Float score; // Doit être un `Double``

    private List<QuestionDTO> questions;
}
