package com.userPI.usersmanagementsystem.dto.levelTest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;

    @NotBlank(message = "Le texte de la question ne peut pas être vide")
    private String questionText;
    private String questionImage;

    @NotBlank(message = "L'option A est obligatoire")
    private String optionA;

    @NotBlank(message = "L'option B est obligatoire")
    private String optionB;

    @NotBlank(message = "L'option C est obligatoire")
    private String optionC;

    private String optionD; // Option facultative

    @NotNull(message = "La réponse correcte est obligatoire")
    private String correctAnswer;
}
