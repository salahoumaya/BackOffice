package tn.esprit.formation.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.formation.Entities.Formation;

@Repository
public interface FormationRepository extends JpaRepository <Formation, Long> {
}
