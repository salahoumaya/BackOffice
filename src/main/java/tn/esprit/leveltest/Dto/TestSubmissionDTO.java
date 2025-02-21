package tn.esprit.leveltest.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestSubmissionDTO {
    private Long testId;
    private Long studentId;
    private LocalDateTime startTime;
    private List<AnswerDTO> answers;
}
