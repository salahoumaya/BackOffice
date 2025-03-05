package tn.esprit.tacheevaluation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tacheevaluation.entity.Formation;
import tn.esprit.tacheevaluation.repository.FormationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FormationService {
    @Autowired
    private FormationRepository formationRepository;

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

}
