package tn.esprit.tacheevaluation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tacheevaluation.entity.Diplome;
import tn.esprit.tacheevaluation.repository.DiplomeRepository;


import java.util.List;
import java.util.Optional;

@Service
public class DiplomeService {
    @Autowired
    private DiplomeRepository diplomeRepository;

    // ✅ Créer un diplôme
    public Diplome createDiplome(Diplome diplome) {
        return diplomeRepository.save(diplome);
    }

    // ✅ Récupérer tous les diplômes
    public List<Diplome> getAllDiplomes() {
        return diplomeRepository.findAll();
    }

    // ✅ Récupérer un diplôme par son ID
    public Optional<Diplome> getDiplomeById(Long id) {
        return diplomeRepository.findById(id);
    }

    // ✅ Supprimer un diplôme
    public void deleteDiplome(Long id) {
        diplomeRepository.deleteById(id);
    }

    public Diplome updateDiplome(Long id, Diplome updatedDiplome) {
        return diplomeRepository.findById(id).map(diplome -> {
            diplome.setDateObtention(updatedDiplome.getDateObtention());
            diplome.setFormation(updatedDiplome.getFormation());
            return diplomeRepository.save(diplome);
        }).orElseThrow(() -> new RuntimeException("Diplôme avec ID " + id + " non trouvé"));
    }

}
