package com.userPI.usersmanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Entretien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long EntretienId;
    private LocalDateTime dateEntretien;
    private String lieu;
    private String note;

}
