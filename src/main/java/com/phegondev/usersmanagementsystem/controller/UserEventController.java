package com.phegondev.usersmanagementsystem.controller;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.phegondev.usersmanagementsystem.dto.EventDTO;
import com.phegondev.usersmanagementsystem.dto.ReservationDTO;
import com.phegondev.usersmanagementsystem.entity.Event;
import com.phegondev.usersmanagementsystem.service.EventService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events/user")
@PreAuthorize("hasRole('USER')")
public class UserEventController {
    @Autowired
    EventService eventService;
    @GetMapping("/upcoming")
    public List<Event> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/reserve")
    public ResponseEntity<String> reserveEvent(@RequestBody ReservationDTO reservationDTO) {
        String message = eventService.reserveEvent(reservationDTO);
        if (message.contains("complet")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }
// Assurez-vous que ce service existe et permet de récupérer un Event

        @GetMapping("/qr/{eventId}")
        public void generateEventQRCode(@PathVariable Long eventId, HttpServletResponse response) {
            try {
                // Construire une URL pointant vers votre front-end (à adapter selon votre besoin)
                String qrContent = "http://localhost:4200/api/events/" + eventId;
                int width = 300;
                int height = 300;
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, width, height);

                response.setContentType("image/png");
                OutputStream outputStream = response.getOutputStream();
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
                outputStream.close();
            } catch (WriterException | IOException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }

        // 2️⃣ Génère un QR Code en se basant sur les informations de l'événement


        // Méthode utilitaire statique pour générer l'image du QR Code à partir d'un Event
        public static BufferedImage generateQRCodeImage(EventDTO event) throws Exception {
            if (event != null) {
                // Construction du contenu du QR Code en utilisant les détails de l'événement
                String qrContent = String.format("Event ID: %d | Title: %s | Date: %s",
                        event.getEventId(), event.getTitle(), event.getScheduledAt().toString());

                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 200, 200);
                return MatrixToImageWriter.toBufferedImage(bitMatrix);
            }
            return null;
        }
    @GetMapping("/download-ticket/{id}")
    public ResponseEntity<byte[]> downloadReservationTicket(@PathVariable Long id) {
        byte[] pdfBytes = eventService.generateReservationPDF(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ticket_reservation.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
    @PostMapping("/add-reservation")
    public ResponseEntity<?> addReservation(@RequestBody ReservationDTO reservationDTO) {
        String message = eventService.reserveEvent(reservationDTO);
        if (message.contains("complet")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("message", message)
            );
        }
        return ResponseEntity.ok(Map.of("message", message));
    }

    @GetMapping("/my-reservations/{userId}")
    public ResponseEntity<List<EventDTO>> getUserReservations(@PathVariable Long userId) {
        List<EventDTO> userReservations = eventService.getReservationsByUserId(userId);
        return ResponseEntity.ok(userReservations);
    }
}


