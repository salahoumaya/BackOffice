package tn.esprit.formation.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.formation.Entities.Examen;
import tn.esprit.formation.Repository.ExamenRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExamenService {
    @Autowired
    private ExamenRepository examenRepository;

    public Examen createExamen(Examen examen) {
        return examenRepository.save(examen);
    }

    public List<Examen> getAllExamens() {
        return examenRepository.findAll();
    }

    public Optional<Examen> getExamenById(Long id) {
        return examenRepository.findById(id);
    }

    public void deleteExamen(Long id) {
        examenRepository.deleteById(id);
    }
    public Examen updateExamen(Long id, Examen updatedExamen) {
        return examenRepository.findById(id).map(examen -> {
            examen.setTitre(updatedExamen.getTitre());
            examen.setNote(updatedExamen.getNote());
            examen.setExamenT(updatedExamen.getExamenT());
            examen.setSession(updatedExamen.getSession());
            examen.setDate(updatedExamen.getDate());
            examen.setDuree(updatedExamen.getDuree());
            examen.setFormation(updatedExamen.getFormation());
            return examenRepository.save(examen);
        }).orElseThrow(() -> new RuntimeException("Examen avec ID " + id + " non trouvé"));
    }

}
