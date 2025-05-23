package com.userPI.usersmanagementsystem.service.Event;




import com.userPI.usersmanagementsystem.dto.EventDTO;

import java.util.List;

public interface IEventService {
    public EventDTO addEvent(EventDTO eventDTO);
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
