package tn.esprit.tacheevaluation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tacheevaluation.entity.Formation;
import tn.esprit.tacheevaluation.entity.OurUsers;
import tn.esprit.tacheevaluation.repository.FormationRepository;
import tn.esprit.tacheevaluation.repository.UsersRepo;

import java.util.List;
import java.util.Optional;

@Service
public class FormationService {
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private UsersRepo usersRepo;
    public Formation createFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    public Optional<Formation> getFormationById(Long id) {
        return formationRepository.findById(id);
    }

    public void deleteFormation(Long id) {
        formationRepository.deleteById(id);
    }
    public Formation updateFormation(Long id, Formation updatedFormation) {
        return formationRepository.findById(id).map(formation -> {
            formation.setTitre(updatedFormation.getTitre());
            formation.setDescription(updatedFormation.getDescription());
            formation.setFormationT(updatedFormation.getFormationT());
            formation.setNiveau(updatedFormation.getNiveau());

            return formationRepository.save(formation);
        }).orElseThrow(() -> new RuntimeException("Formation avec ID " + id + " non trouvée"));
    }

    public List<Formation> getFormationsusers(Integer id) {
        OurUsers ourUsers = usersRepo.findById(id).get();

        return formationRepository.findAllByOurUsers(ourUsers);
    }

    public String assignUserToFormation(Integer userId, Long formationId) {
        Optional<OurUsers> userOpt = usersRepo.findById(userId);
        Optional<Formation> formationOpt = formationRepository.findById(formationId);

        if (userOpt.isPresent() && formationOpt.isPresent()) {
            OurUsers user = userOpt.get();
            Formation formation = formationOpt.get();
            if (!formation.getOurUsers().contains(user)) {
                formation.getOurUsers().add(user);
                user.getFormations().add(formation);
                formationRepository.save(formation);

                return "Utilisateur affecté à la formation avec succès.";
            } else {
                return "Utilisateur déjà inscrit à cette formation.";
            }
        }
        return "Utilisateur ou formation introuvable.";
    }

}
