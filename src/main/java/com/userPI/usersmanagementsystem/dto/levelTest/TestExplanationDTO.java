package com.userPI.usersmanagementsystem.dto.levelTest;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestExplanationDTO {
    private String question;
    @JsonProperty("user_answer")
    private String userAnswer;

    @JsonProperty("correct_answer")
    private String correctAnswer;
}
