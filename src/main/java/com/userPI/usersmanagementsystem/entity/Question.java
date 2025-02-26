package com.userPI.usersmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String testTitle;


    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;
}
