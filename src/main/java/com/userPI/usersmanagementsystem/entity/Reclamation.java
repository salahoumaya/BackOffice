package com.userPI.usersmanagementsystem.entity;

import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reclamation {
    @Id
    @Column(name = "reclamation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String type; // EVENT, TRAINING, SUJET_PFE
    @Column(name = "is_read")
    @NotNull
    private boolean read = false;

    @NotBlank
    private String targetName;

    private String status = "OPEN"; // OPEN, IN_PROGRESS, RESOLVED, REJECTED

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime resolutionDate;
    private String responseMessage; // ✅ pour stocker la réponse de l'admin


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private OurUsers user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @ManyToOne
    @JoinColumn(name = "sujet_pfe_id")
    private SujetPfe sujetPfe;

    // Méthode pour mettre à jour le statut
    public void updateStatus(String newStatus) {
        this.status = newStatus;
        if ("RESOLVED".equals(newStatus) || "REJECTED".equals(newStatus)) {
            this.resolutionDate = LocalDateTime.now();
        }
    }
}