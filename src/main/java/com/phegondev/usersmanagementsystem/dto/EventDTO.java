package com.phegondev.usersmanagementsystem.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long eventId;
    private String title;
    private String description;
    private LocalDateTime scheduledAt;
    private int capacity; // Capacité maximale
    private int reservedSeats = 0; // Places réservées par défaut à 0

    // Méthode pour vérifier s'il reste des places disponibles
    public boolean isFull() {
        return reservedSeats >= capacity;
    }

    // Méthode pour réserver une place
    public void reserveSeat() {
        if (!isFull()) {
            reservedSeats++;
        }
    }}

