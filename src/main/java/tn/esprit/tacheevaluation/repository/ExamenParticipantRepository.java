package tn.esprit.tacheevaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tacheevaluation.entity.Examen;
import tn.esprit.tacheevaluation.entity.ExamenParticipant;
import tn.esprit.tacheevaluation.entity.OurUsers;

import java.util.List;

@Repository
public interface ExamenParticipantRepository extends JpaRepository<ExamenParticipant, Long> {
    List<ExamenParticipant> findByExamen(Examen examen);
    List<ExamenParticipant> findByUser(OurUsers user);
    ExamenParticipant getExamenParticipantByUser(OurUsers user);

}
