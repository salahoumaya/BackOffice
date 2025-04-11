package com.phegondev.usersmanagementsystem.service;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.phegondev.usersmanagementsystem.dto.EventDTO;
import com.phegondev.usersmanagementsystem.dto.ReservationDTO;
import com.phegondev.usersmanagementsystem.entity.Event;
import com.phegondev.usersmanagementsystem.entity.OurUsers;
import com.phegondev.usersmanagementsystem.entity.Reservation;
import com.phegondev.usersmanagementsystem.repository.EventRepository;
import com.phegondev.usersmanagementsystem.repository.ReservationRepository;
import com.phegondev.usersmanagementsystem.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    // 1️⃣ Ajouter un événement (Admin)
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
        event.setCapacity(eventDTO.getCapacity());

        Event updatedEvent = eventRepository.save(event);
        return new EventDTO(updatedEvent.getEventId(), updatedEvent.getTitle(), updatedEvent.getDescription(),
                updatedEvent.getScheduledAt(), updatedEvent.getCapacity(), updatedEvent.getReservedSeats());
    }

    // 3️⃣ Supprimer un événement (Admin)
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    // 4️⃣ Voir les événements à venir (Étudiant)
    public List<EventDTO> getUpcomingEvents() {
        return eventRepository.findByScheduledAtAfter(LocalDateTime.now()).stream()
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
        reservation.setContactEmail(reservationDTO.getContactEmail());
        reservation.setReservationDate(LocalDateTime.now());

        reservationRepository.save(reservation);

        return "Réservation effectuée avec succès !";
    }
    public byte[] generateReservationPDF(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Ticket de Réservation"));
            document.add(new Paragraph("Événement : " + reservation.getEvent().getTitle()));
            document.add(new Paragraph("Date : " + reservation.getEvent().getScheduledAt()));
            document.add(new Paragraph("Nom de l'Utilisateur : " + reservation.getUser().getUsername()));
            document.add(new Paragraph("Email : " + reservation.getContactEmail()));
            document.add(new Paragraph("Date de Réservation : " + reservation.getReservationDate()));

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }

}}
