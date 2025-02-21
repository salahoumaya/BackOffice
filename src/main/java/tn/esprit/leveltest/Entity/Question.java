package tn.esprit.leveltest.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Question")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;
    private String answerOptions; // JSON format (A,B,C,D)
    private String correctAnswer;


    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;
}
