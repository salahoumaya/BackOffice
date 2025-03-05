package tn.esprit.tacheevaluation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tn.esprit.tacheevaluation.service.NotificationService;

import javax.management.Notification;
import java.util.Map;
public class NotificationController{
    private NotificationService notificationService;

    @PostMapping("/send")
public ResponseEntity<String> sendEmail(@RequestBody Map<String, String> request) {
    if (!request.containsKey("to") || !request.containsKey("sujet") || !request.containsKey("message")) {
        return ResponseEntity.badRequest().body("❌ Données invalides ! Vérifie que `to`, `sujet`, et `message` sont présents.");
    }

    String to = request.get("to");
    String sujet = request.get("sujet");
    String message = request.get("message");

    
    notificationService.envoyerRappelEmail(to, sujet, message);
    return ResponseEntity.ok("📧 Email envoyé avec succès à " + to);
}
} 