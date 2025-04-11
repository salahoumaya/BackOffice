package com.userPI.usersmanagementsystem.entity;

import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Diplome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;

    public String getPath() {
        return path;
    }

    public Diplome setPath(String path) {
        this.path = path;
        return this;
    }

    public LocalDate getDateObtention() {
        return dateObtention;
    }

    public void setDateObtention(LocalDate dateObtention) {
        this.dateObtention = dateObtention;
    }

    public Training getFormation() {
        return formation;
    }

    public void setFormation(Training formation) {
        this.formation = formation;
    }

    private LocalDate dateObtention;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Training formation;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public OurUsers getUser() {
        return user;
    }

    public void setUser(OurUsers user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private OurUsers user;

}
