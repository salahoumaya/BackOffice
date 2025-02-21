package tn.esprit.leveltest.Dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TestDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime scheduledAt;
    private List<QuestionDTO> questions;
}
