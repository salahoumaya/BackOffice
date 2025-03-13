package com.userPI.usersmanagementsystem.entity;

import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reclamationId;

    private String title;
    private String description;

    // Lien vers l'utilisateur qui fait la réclamation
    @ManyToOne
    @JoinColumn(name = "user_id")
    private OurUsers user;

    // Une réclamation peut concerner un Event, un Training ou un SujetPFE
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = true)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = true)
    private Training training;

    @ManyToOne
    @JoinColumn(name = "sujetPfe_id", nullable = true)
    private SujetPfe sujetPfe;


}
