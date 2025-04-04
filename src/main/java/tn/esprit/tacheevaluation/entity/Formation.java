package tn.esprit.tacheevaluation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.twilio.rest.numbers.v2.regulatorycompliance.bundle.Evaluation;
import jakarta.persistence.*;

import java.util.List;


@Entity
    public class Formation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String titre;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getNiveau() {
            return niveau;
        }

        public void setNiveau(String niveau) {
            this.niveau = niveau;
        }

        public TypeFormation getFormationT() {
            return FormationT;
        }

        public void setFormationT(TypeFormation formationT) {
            FormationT = formationT;
        }

        public String getTitre() {
            return titre;
        }

        public void setTitre(String titre) {
            this.titre = titre;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        private String description;
        @Enumerated(EnumType.STRING)
        private  TypeFormation FormationT;
        private String niveau;
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "ourusers_formations",
            joinColumns = @JoinColumn(name = "formation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
        private List<OurUsers> ourUsers;
         @JsonIgnore
        @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
        private List<Examen> examenList;
    @JsonIgnore
        @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
        private List<Diplome> diplomeList;

    public List<OurUsers> getOurUsers() {
        return ourUsers;
    }

    public Formation setOurUsers(List<OurUsers> ourUsers) {
        this.ourUsers = ourUsers;
        return this;
    }

    public List<Examen> getExamenList() {
        return examenList;
    }

    public Formation setExamenList(List<Examen> examenList) {
        this.examenList = examenList;
        return this;
    }

    public List<Diplome> getDiplomeList() {
        return diplomeList;
    }

    public Formation setDiplomeList(List<Diplome> diplomeList) {
        this.diplomeList = diplomeList;
        return this;
    }
}



