package com.userPI.usersmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@Entity
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Enumerated(EnumType.STRING)
    private TypeExamen ExamenT; // oral ou écrit
    @OneToMany(mappedBy = "examen", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ExamenParticipant> participants;
    private String session; // principale ou contrôle
    private LocalDate date;
    private Integer duree; // en minutes

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private OurUsers createdBy; // Admin ou Modérateur qui a créé l'examen

    @OneToMany(mappedBy = "exam",fetch = FetchType.LAZY)
    private List<QuestionEx> questions;
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "examen_participants",
//            joinColumns = @JoinColumn(name = "examen_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
//    private List<OurUsers> participants;

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public Examen setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitre() {
        return titre;
    }

    public Examen setTitre(String titre) {
        this.titre = titre;
        return this;
    }


    public TypeExamen getExamenT() {
        return ExamenT;
    }

    public Examen setExamenT(TypeExamen examenT) {
        ExamenT = examenT;
        return this;
    }

    public List<ExamenParticipant> getParticipants() {
        return participants;
    }

    public Examen setParticipants(List<ExamenParticipant> participants) {
        this.participants = participants;
        return this;
    }

    public String getSession() {
        return session;
    }

    public Examen setSession(String session) {
        this.session = session;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Examen setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Integer getDuree() {
        return duree;
    }

    public Examen setDuree(Integer duree) {
        this.duree = duree;
        return this;
    }

    public Course getFormation() {
        return course;
    }

    public Examen setFormation(Course formation) {
        this.course = formation;
        return this;
    }

    public OurUsers getCreatedBy() {
        return createdBy;
    }

    public Examen setCreatedBy(OurUsers createdBy) {
        this.createdBy = createdBy;
        return this;
    }
}
