package tn.esprit.tacheevaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tacheevaluation.service.NotificationService;

import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody Map<String, String> request) {
        String to = request.get("to");
        String sujet = request.get("sujet");
        String message = request.get("message");

        notificationService.envoyerEmail(to, sujet, message);
        return ResponseEntity.ok("📧 Email envoyé avec succès à " + to);
    }
}
