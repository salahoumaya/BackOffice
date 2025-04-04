package tn.esprit.tacheevaluation.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.tacheevaluation.entity.Examen;
import tn.esprit.tacheevaluation.entity.Formation;
import tn.esprit.tacheevaluation.entity.OurUsers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {
        List<Examen> findAllByFormation(Formation formation);
        @Query("SELECT e FROM Examen e JOIN e.participants p WHERE p.id = :userId")
        List<Examen> findByParticipantsId(@Param("userId") Long userId);

    List<Examen> findAllByParticipants(OurUsers ourUsers);


    List<Examen> findByDate(LocalDate dateCible);
}


