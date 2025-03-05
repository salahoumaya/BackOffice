package tn.esprit.tacheevaluation.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Entity
public class Diplome {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    public LocalDate getDateObtention() {
        return dateObtention;
    }

    public void setDateObtention(LocalDate dateObtention) {
        this.dateObtention = dateObtention;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    private LocalDate dateObtention;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

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
