package tn.esprit.formation.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.formation.Entities.Diplome;

@Repository
public interface DiplomeRepository extends JpaRepository<Diplome, Long> {
}
