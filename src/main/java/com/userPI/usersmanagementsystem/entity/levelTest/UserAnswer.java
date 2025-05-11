package com.userPI.usersmanagementsystem.entity.levelTest;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userAnswer;
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "test_submission_id", nullable = false)
    private TestSubmission testSubmission;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;


    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
