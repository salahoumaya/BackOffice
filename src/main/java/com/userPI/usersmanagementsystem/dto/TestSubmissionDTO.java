package com.userPI.usersmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class TestSubmissionDTO {
    private Long testId; // ID du test sélectionné
    private List<AnswerDTO> answers; // Liste des réponses utilisateur
}
