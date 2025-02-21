package tn.esprit.leveltest.Dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;
    private String questionText;
    private String answerOptions;
    private String correctAnswer;


}
