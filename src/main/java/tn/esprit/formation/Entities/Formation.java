package tn.esprit.formation.Entities;

import jakarta.persistence.*;


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
    }



