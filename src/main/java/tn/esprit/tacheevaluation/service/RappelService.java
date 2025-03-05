package tn.esprit.tacheevaluation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.tacheevaluation.entity.Examen;
import tn.esprit.tacheevaluation.repository.ExamenRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RappelService {

    @Autowired
    private ExamenRepository examenRepository;

    @Autowired
    private NotificationService notificationService;

    // Planification de l'envoi des emails chaque jour à 8h du matin
    @Scheduled(cron = "0 0 8 * * ?")
    public void envoyerRappelExamen() {
        List<Examen> examens = examenRepository.findAll();
        LocalDateTime maintenant = LocalDateTime.now();

        for (Examen examen : examens) {
            LocalDateTime dateExamen = examen.getDate();

            // Si l'examen est prévu pour demain, on envoie un rappel
            if (dateExamen.minusDays(1).toLocalDate().equals(maintenant.toLocalDate())) {
                examen.getParticipants().forEach(etudiant -> {
                    String email = etudiant.getEmail();
                    String sujet = "📌 Rappel d'Examen";
                    String message = "Bonjour " + etudiant.getName() +
                            ", votre examen \"" + examen.getTitre() +
                            "\" est prévu demain à " + dateExamen.getHour() + "h.";

                    notificationService.envoyerEmail(email, sujet, message);
                });
            }
        }
    }
}
