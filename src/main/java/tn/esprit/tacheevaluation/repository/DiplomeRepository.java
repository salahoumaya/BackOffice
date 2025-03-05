package tn.esprit.tacheevaluation.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tacheevaluation.entity.Diplome;


@Repository
public interface DiplomeRepository extends JpaRepository<Diplome, Long> {
}
