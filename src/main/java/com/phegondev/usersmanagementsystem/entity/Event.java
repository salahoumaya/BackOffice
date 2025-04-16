package com.phegondev.usersmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    private String title;
    private String description;
    private LocalDateTime scheduledAt;
    private int capacity; // Capacité maximale
    private int reservedSeats = 0; // Places réservées par défaut à 0
    @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    // Méthode pour vérifier s'il reste des places disponibles
    public boolean isFull() {
        return reservedSeats >= capacity;
    }

    // Méthode pour réserver une place
    public void reserveSeat() {
        if (!isFull()) {
            reservedSeats++;
        }
    }

}

