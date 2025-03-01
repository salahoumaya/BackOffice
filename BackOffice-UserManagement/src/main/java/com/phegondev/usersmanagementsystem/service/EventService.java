package com.phegondev.usersmanagementsystem.service;

import com.phegondev.usersmanagementsystem.dto.EventDTO;
import com.phegondev.usersmanagementsystem.entity.Event;
import com.phegondev.usersmanagementsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService implements IEventService {
    @Autowired
    private EventRepository eventRepository;

    // 1️⃣ Ajouter un événement (Admin)
    public EventDTO addEvent(EventDTO eventDTO) {
        Event event = new Event();
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setScheduledAt(eventDTO.getScheduledAt());

        Event savedEvent = eventRepository.save(event);
        return new EventDTO(savedEvent.getEventId(), savedEvent.getTitle(), savedEvent.getDescription(),
                savedEvent.getScheduledAt());
    }

    // 2️⃣ Modifier un événement (Admin)
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setScheduledAt(eventDTO.getScheduledAt());

        Event updatedEvent = eventRepository.save(event);
        return new EventDTO(updatedEvent.getEventId(), updatedEvent.getTitle(), updatedEvent.getDescription(),
                updatedEvent.getScheduledAt());
    }

    // 3️⃣ Supprimer un événement (Admin)
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    // 4️⃣ Voir les événements à venir (Étudiant)
    public List<EventDTO> getUpcomingEvents() {
        return eventRepository.findByScheduledAtAfter(LocalDateTime.now()).stream()
                .map(event -> new EventDTO(event.getEventId(), event.getTitle(), event.getDescription(),
                        event.getScheduledAt()))
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EventDTO getEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            // Conversion de l'entité Event en DTO
            EventDTO eventDTO = new EventDTO();
            eventDTO.setEventId(event.get().getEventId());
            eventDTO.setTitle(event.get().getTitle());
            eventDTO.setDescription(event.get().getDescription());
            eventDTO.setScheduledAt(event.get().getScheduledAt());
            return eventDTO;
        } else {
            throw new RuntimeException("Événement non trouvé avec l'ID : " + id);
        }
    }

    private EventDTO convertToDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventId(event.getEventId());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setScheduledAt(event.getScheduledAt());
        return eventDTO;
    }
}
