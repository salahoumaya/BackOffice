package tn.esprit.tacheevaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tacheevaluation.entity.Formation;
import tn.esprit.tacheevaluation.entity.OurUsers;

import java.util.List;


@Repository
public interface FormationRepository extends JpaRepository <Formation, Long> {

    List<Formation> findAllByOurUsers(OurUsers ourUsers);
}
