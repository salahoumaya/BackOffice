package com.userPI.usersmanagementsystem.entity.levelTest;

import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private OurUsers user;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    private double score;
    private LocalDateTime submittedAt;
    private LocalDateTime startTime;

    @OneToMany(mappedBy = "testSubmission", cascade = CascadeType.ALL)
    private List<UserAnswer> userAnswers;
}
