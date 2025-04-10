package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.entity.Examen;
import com.userPI.usersmanagementsystem.entity.ExamenParticipant;
import com.userPI.usersmanagementsystem.repository.ExamenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
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
