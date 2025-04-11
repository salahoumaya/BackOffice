package com.phegondev.usersmanagementsystem.service;



import com.phegondev.usersmanagementsystem.dto.EventDTO;
import com.phegondev.usersmanagementsystem.entity.Event;

import java.util.List;

public interface IEventService {
    EventDTO addEvent(EventDTO eventDTO);
    public EventDTO updateEvent(Long id, EventDTO eventDTO);
    public void deleteEvent(Long id);
    public List<EventDTO> getUpcomingEvents();
    // Envoyer un rappel aux participants avant l’événement
   // public void sendReminderNotifications();
    // Inscription d'un utilisateur à un événement
    //public void registerUserToEvent(Long eventId, Long userId);

    // Récupérer la liste des participants d'un événement
   // public List<UserDTO> getEventParticipants(Long eventId);


}
