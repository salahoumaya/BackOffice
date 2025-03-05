package tn.esprit.tacheevaluation.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Examen {
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

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private OurUsers createdBy; // Admin ou Modérateur qui a créé l'examen

    @ManyToMany
    @JoinTable(
            name = "examen_participants",
            joinColumns = @JoinColumn(name = "examen_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<OurUsers> participants; // Étudiants inscrits

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public Double getNote() { return note; }
    public void setNote(Double note) { this.note = note; }

    public TypeExamen getExamenT() { return ExamenT; }
    public void setExamenT(TypeExamen examenT) { ExamenT = examenT; }

    public String getSession() { return session; }
    public void setSession(String session) { this.session = session; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public Integer getDuree() { return duree; }
    public void setDuree(Integer duree) { this.duree = duree; }

    public Formation getFormation() { return formation; }
    public void setFormation(Formation formation) { this.formation = formation; }

    public OurUsers getCreatedBy() { return createdBy; }
    public void setCreatedBy(OurUsers createdBy) { this.createdBy = createdBy; }

    public List<OurUsers> getParticipants() { return participants; }
    public void setParticipants(List<OurUsers> participants) { this.participants = participants; }
}
