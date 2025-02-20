package tn.esprit.formation.Entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import tn.esprit.formation.Entities.Formation;
import tn.esprit.formation.Entities.TypeExamen;

import java.util.Date;

@Entity
public class Examen {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private Double note;

    @Enumerated(EnumType.STRING)
    private TypeExamen ExamenT; // oral ou écrit

    private String session; // principale ou contrôle
    private Date date;
    private Integer duree; // en minutes

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public TypeExamen getExamenT() {
        return ExamenT;
    }

    public void setExamenT(TypeExamen examenT) {
        ExamenT = examenT;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;


}
