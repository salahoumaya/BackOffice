package tn.esprit.tacheevaluation.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.tacheevaluation.entity.Examen;

import java.util.List;


@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {

        @Query("SELECT e FROM Examen e JOIN e.participants p WHERE p.id = :userId")
        List<Examen> findByParticipantsId(@Param("userId") Long userId);
    }


