package com.phegondev.usersmanagementsystem.controller;

import com.phegondev.usersmanagementsystem.dto.EventDTO;
import com.phegondev.usersmanagementsystem.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:4200")

public class EventController {
    @Autowired
    private EventService eventService;
    @PreAuthorize("hasRole('ADMIN')") // Vérifie que l'utilisateur est admin
    // 1️⃣ Ajouter un événement (Admin)
    @PostMapping("/admin/create-event")
    public ResponseEntity<EventDTO> addEvent(@RequestBody EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.addEvent(eventDTO));
    }

    // 2️⃣ Modifier un événement (Admin)
    @PreAuthorize("hasRole('ADMIN')") // Vérifie que l'utilisateur est admin
    @PutMapping("/admin/update-event/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable("id") Long id, @RequestBody EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventDTO));
    }
    @PreAuthorize("hasRole('ADMIN')") // Vérifie que l'utilisateur est admin
    // 3️⃣ Supprimer un événement (Admin)
    @DeleteMapping("/admin/delete-event/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
    // 4️⃣ Voir les événements à venir (Étudiant)
    @PreAuthorize("hasRole('ADMIN')") // Vérifie que l'utilisateur est admin
    @GetMapping("/admin/upcoming")
    public List<EventDTO> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }
    @GetMapping("/admin/all")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    // 1️⃣ Récupérer un événement par son ID
    @PreAuthorize("hasRole('ADMIN')") // Vérifie que l'utilisateur est admin
    @GetMapping("/admin/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable("id") Long id) {
        EventDTO eventDTO = eventService.getEventById(id);
        return ResponseEntity.ok(eventDTO);
    }

}
