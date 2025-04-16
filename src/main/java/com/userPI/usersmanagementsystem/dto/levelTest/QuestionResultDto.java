package com.userPI.usersmanagementsystem.dto.levelTest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResultDto {
    private Long questionId;
    private String questionText;
    private String questionImage;
    private String correctAnswer;
    private String userAnswer;
    private boolean isCorrect;


}
