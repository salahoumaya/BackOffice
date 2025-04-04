package tn.esprit.tacheevaluation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tacheevaluation.entity.Examen;
import tn.esprit.tacheevaluation.entity.ExamenParticipant;
import tn.esprit.tacheevaluation.entity.OurUsers;
import tn.esprit.tacheevaluation.repository.ExamenRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class RappelService {

    @Autowired
    private ExamenRepository examenRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
   // @Scheduled(cron = "* 0/1 * * * ?")
    public void envoyerRappelExamens() {
        LocalDate dateCible = LocalDate.now().plusDays(2);
        List<Examen> examens = examenRepository.findByDate(dateCible);

        System.out.println(examens.size() + " examens trouvés pour " + dateCible);

        for (Examen examen : examens) {
            examen.getParticipants().size();

            for (ExamenParticipant user : examen.getParticipants()) {
                String email = user.getUser().getEmail();
                String sujet = "📅 Rappel d'Examen - " + examen.getTitre();
                String message = "Bonjour " + user.getUser().getName() + ",\n\n"
                        + "Ceci est un rappel que votre examen '" + examen.getTitre()
                        + "' est prévu le " + examen.getDate() + ".\n\n"
                        + "Bonne chance ! 📚\n\n"
                        + "Cordialement,\nL'équipe de gestion des examens.";

                notificationService.envoyerEmail(email, sujet, message);
            }
        }
    }
}
