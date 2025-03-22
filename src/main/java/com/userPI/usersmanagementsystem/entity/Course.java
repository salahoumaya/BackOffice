package com.userPI.usersmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 100 characters")
    private String title;
    @NotBlank(message = "Content cannot be empty")
    @Size(min = 100, max = 1000, message = "Content must be between 10 and 500 characters")
    private String content;

    @JsonIgnore
    @ManyToOne
    private Training training;
}
