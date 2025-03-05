package tn.esprit.tacheevaluation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tacheevaluation.entity.Examen;
import tn.esprit.tacheevaluation.entity.OurUsers;
import tn.esprit.tacheevaluation.entity.UserRole;
import tn.esprit.tacheevaluation.repository.ExamenRepository;
import tn.esprit.tacheevaluation.repository.UsersRepo;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExamenService {
    @Autowired
    private ExamenRepository examenRepository;

    @Autowired
    private UsersRepo usersRepo;

    // ✅ Voir tous les examens (ETUDIANT, ADMIN, MODERATOR)
    public List<Examen> getAllExamens() {
        return examenRepository.findAll();
    }

    // ✅ Ajouter un examen (ADMIN, MODERATOR)
    public Examen addExamen(Examen examen, Integer userId) {
        OurUsers user = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        examen.setCreatedBy(user);
        return examenRepository.save(examen);
    }

    // ✅ Modifier un examen (ADMIN, MODERATOR)
    public Examen updateExamen(Long id, Examen examenDetails) {
        return examenRepository.findById(id).map(examen -> {
            examen.setTitre(examenDetails.getTitre());
            examen.setDate(examenDetails.getDate());
            examen.setDuree(examenDetails.getDuree());
            return examenRepository.save(examen);
        }).orElseThrow(() -> new RuntimeException("Examen non trouvé"));
    }

    // ✅ Supprimer un examen (ADMIN) avec un message de confirmation
    public String deleteExamen(Long id) {
        if (examenRepository.existsById(id)) {
            examenRepository.deleteById(id);
            return "Examen avec ID " + id + " supprimé avec succès.";
        } else {
            throw new RuntimeException("Examen avec ID " + id + " non trouvé.");
        }
    }


    public List<String> getParticipantsByExamen(Long examenId) {
        Examen examen = examenRepository.findById(examenId)
                .orElseThrow(() -> new RuntimeException("Examen non trouvé"));

        return examen.getParticipants().stream()
                .map(user -> "Nom: " + user.getName() + ", Email: " + user.getEmail())
                .collect(Collectors.toList());
    }

    public String participerExamen(Long examenId, Integer userId) {
        Examen examen = examenRepository.findById(examenId)
                .orElseThrow(() -> new RuntimeException("Examen non trouvé"));

        OurUsers etudiant = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));

        // Vérifie si l'étudiant est déjà inscrit
        if (examen.getParticipants().contains(etudiant)) {
            return "Tu es déjà inscrit à l'examen : " + examen.getTitre() + " (ID: " + examen.getId() + ")";
        }

        // Ajouter l'étudiant à l'examen
        examen.getParticipants().add(etudiant);
        examenRepository.save(examen);

        return "Tu es inscrit à l'examen : " + examen.getTitre() + " (ID: " + examen.getId() + ")";
    }








}
