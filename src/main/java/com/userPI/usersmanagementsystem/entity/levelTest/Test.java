package com.userPI.usersmanagementsystem.entity.levelTest;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime scheduledAt;
    private int duration;
    private Float score;
    private String image;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<TestSubmission> submissions;
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<Question> questions;
}
