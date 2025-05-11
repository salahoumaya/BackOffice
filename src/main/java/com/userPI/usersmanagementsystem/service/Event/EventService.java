package com.userPI.usersmanagementsystem.service.Event;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.userPI.usersmanagementsystem.dto.EventDTO;
import com.userPI.usersmanagementsystem.dto.ReservationDTO;
import com.userPI.usersmanagementsystem.entity.Event;
import com.userPI.usersmanagementsystem.entity.Reservation;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.repository.EventRepository;
import com.userPI.usersmanagementsystem.repository.ReservationRepository;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import com.userPI.usersmanagementsystem.service.Event.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService implements IEventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    ReservationRepository reservationRepository;

    public EventDTO addEvent(EventDTO eventDTO) {
        Event event = new Event();
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setScheduledAt(eventDTO.getScheduledAt());
        event.setCapacity(eventDTO.getCapacity());

        Event savedEvent = eventRepository.save(event);
        return new EventDTO(savedEvent.getEventId(), savedEvent.getTitle(), savedEvent.getDescription(),
                savedEvent.getScheduledAt(), savedEvent.getCapacity(), savedEvent.getReservedSeats());
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
                updatedEvent.getScheduledAt(), updatedEvent.getCapacity(), updatedEvent.getReservedSeats());
    }

    // 3️⃣ Supprimer un événement (Admin)
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    // 4️⃣ Voir les événements à venir (Étudiant)
    public List<Event> getUpcomingEvents() {
        return eventRepository.findAll();
    }

    @Override
    public List<EventDTO> getReservationsByUserId(Long userId) {
        List<Event> reservedEvents = reservationRepository.findEventsByUserId(userId);
        return reservedEvents.stream()
                .map(event -> new EventDTO(event.getEventId(), event.getTitle(), event.getDescription(),
                        event.getScheduledAt(), event.getCapacity(), event.getReservedSeats()))
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
            eventDTO.setCapacity(event.get().getCapacity());
            eventDTO.setReservedSeats(event.get().getReservedSeats());
            eventDTO.setScheduledAt(event.get().getScheduledAt());
            return eventDTO;
        } else {
            throw new RuntimeException("Événement non trouvé avec l'ID : " + id);
        }
    }


    public String reserveEvent(ReservationDTO reservationDTO) {
        Event event = eventRepository.findById(reservationDTO.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.isFull()) {
            return "Cet événement est complet !";
        }

        if (reservationDTO.getUserId() == null) {
            throw new RuntimeException("User ID is missing in the request!");
        }

        OurUsers user = usersRepo.findById(reservationDTO.getUserId().intValue())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Réserver une place
        event.reserveSeat();
        eventRepository.save(event);

        // Enregistrer la réservation
        Reservation reservation = new Reservation();
        reservation.setEvent(event);
        reservation.setUser(user);
        reservation.setContactEmail(user.getEmail());
        reservation.setReservationDate(LocalDateTime.now());

        reservationRepository.save(reservation);

        return "Réservation effectuée avec succès !";
    }


}