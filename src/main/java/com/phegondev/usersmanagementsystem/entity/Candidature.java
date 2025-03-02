package com.phegondev.usersmanagementsystem.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Candidature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long CandidatId;
    private String nom;
    private String prenom;
    private String email;
    private String cv;
    private String specialite;
    @Enumerated(EnumType.STRING)
    private etatCandidature statut;



}

