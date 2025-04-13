package com.userPI.usersmanagementsystem.repository;

import com.userPI.usersmanagementsystem.entity.Examen;
import com.userPI.usersmanagementsystem.entity.ExamenParticipant;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ExamenParticipantRepository extends JpaRepository<ExamenParticipant, Long> {
    List<ExamenParticipant> findByExamen(Examen examen);
    List<ExamenParticipant> findByUser(OurUsers user);
    ExamenParticipant findAllByUserAndExamen(OurUsers user,Examen examen);

    ExamenParticipant getExamenParticipantByUser(OurUsers user);

    List<ExamenParticipant> findByUserAndExamenIn(OurUsers user, List<Examen> examens);
}
