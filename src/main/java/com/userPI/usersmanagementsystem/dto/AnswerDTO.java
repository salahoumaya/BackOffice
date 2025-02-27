package com.userPI.usersmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {
    private Long questionId; // ID de la question
    private String userAnswer;
}
