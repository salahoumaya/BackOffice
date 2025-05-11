package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.dto.EventDTO;
import com.userPI.usersmanagementsystem.entity.Event;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.repository.UsersRepo;


import com.userPI.usersmanagementsystem.service.Event.EmailServiceEvent;
import com.userPI.usersmanagementsystem.service.Event.EventService;
import com.userPI.usersmanagementsystem.service.user.UsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:4200")

public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private UsersManagementService usersManagementService;
    @Autowired
    private EmailServiceEvent emailService;
    @Autowired
    UsersRepo usersRepo;


    @PreAuthorize("hasRole('ADMIN')") // Vérifie que l'utilisateur est admin
    @PostMapping("/admin/create-event")
    public ResponseEntity<EventDTO> addEvent(@RequestBody EventDTO eventDTO) {
        EventDTO createdEvent = eventService.addEvent(eventDTO);

        // Récupérer tous les emails des utilisateurs inscrits
        List<OurUsers> users = usersRepo.findAll();

        List<String> emails = users.stream()
                .map(OurUsers::getEmail)
                .filter(email -> email != null && !email.isEmpty())
                .collect(Collectors.toList());
        String subject = "Nouveau Événement : " + eventDTO.getTitle();
        String message = "Bonjour,\n\nUn nouvel événement a été ajouté : \n\n" +
                "Titre: " + eventDTO.getTitle() + "\n" +
                "Date: " + eventDTO.getScheduledAt() + "\n" +
                "Description: " + eventDTO.getDescription() + "\n\n" +
                "Cordialement,\nL'équipe";

        for (String email : emails) {
            System.out.println("📧 Tentative d'envoi de l'email à : " + email);
            emailService.sendSimpleMail(email, subject, message);
        }

        return ResponseEntity.ok(createdEvent);
    }


    // 2️⃣ Modifier un événement (Admin)
    @PreAuthorize("hasRole('ADMIN')") // Vérifie que l'utilisateur est admin
    @PutMapping("/admin/update-event/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable("id") Long id, @RequestBody EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventDTO));
    }
    @PreAuthorize("hasRole('ADMIN')") // Vérifie que l'utilisateur est admin
    @DeleteMapping("/admin/delete-event/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
    // 4️⃣ Voir les événements à venir (Étudiant)
    @PreAuthorize("hasRole('ADMIN')") // Vérifie que l'utilisateur est admin
    @GetMapping("/admin/upcoming")
    public List<Event> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }



    // 1️⃣ Récupérer un événement par son ID
    @PreAuthorize("hasRole('ADMIN')") // Vérifie que l'utilisateur est admin
    @GetMapping("/admin/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable("id") Long id) {
        EventDTO eventDTO = eventService.getEventById(id);
        return ResponseEntity.ok(eventDTO);
    }




}