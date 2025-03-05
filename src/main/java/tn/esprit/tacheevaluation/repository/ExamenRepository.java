package tn.esprit.tacheevaluation.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tacheevaluation.entity.Examen;


@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {}
