package com.userPI.usersmanagementsystem.repository;

import com.userPI.usersmanagementsystem.dto.UserNoteDTO;
import com.userPI.usersmanagementsystem.entity.Examen;
import com.userPI.usersmanagementsystem.entity.ExamenParticipant;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ExamenParticipantRepository extends JpaRepository<ExamenParticipant, Long> {
    List<ExamenParticipant> findByExamen(Examen examen);
    List<ExamenParticipant> findByUser(OurUsers user);
    ExamenParticipant findAllByUserAndExamen(OurUsers user,Examen examen);

    ExamenParticipant getExamenParticipantByUser(OurUsers user);
    @Query("SELECT ep.user.id AS userId, AVG(ep.note) AS moyenne " +
            "FROM ExamenParticipant ep " +
            "WHERE ep.examen.course.training.id = :formationId " +
            "GROUP BY ep.user.id")
    List<UserNoteDTO> findUserMoyenneByFormationId(@Param("formationId") Long formationId);
    List<ExamenParticipant> findByUserAndExamenIn(OurUsers user, List<Examen> examens);
}
