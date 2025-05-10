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
    private String type;

    @Column(name = "is_read")
    @NotNull
    private boolean read = false;

    @NotBlank
    private String targetName;

    private String status = "OPEN";

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime resolutionDate;
    private String responseMessage;

    @Column(name = "auto_processed")
    private Boolean autoProcessed = false;

    @Column(name = "priority")
    private Boolean priority;

    private String sentiment;

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


    public void updateStatus(String newStatus) {
        this.status = newStatus;
        if ("RESOLVED".equals(newStatus) || "REJECTED".equals(newStatus)) {
            this.resolutionDate = LocalDateTime.now();
        }
    }


    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }
}
