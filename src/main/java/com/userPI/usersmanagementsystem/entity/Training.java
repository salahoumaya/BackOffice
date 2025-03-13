package com.userPI.usersmanagementsystem.entity;


import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 50, message = "Title must be between 5 and 50 characters")
    private String title;

    @NotBlank(message = "Level is required")
    private String level;

    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    private typeTraining typeTraning;


}
