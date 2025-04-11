package com.userPI.usersmanagementsystem.repository;


import com.userPI.usersmanagementsystem.entity.Course;
import com.userPI.usersmanagementsystem.entity.Examen;

import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;


@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {
        List<Examen> findAllByCourse(Course formation);
        @Query("SELECT e FROM Examen e JOIN e.participants p WHERE p.id = :userId")
        List<Examen> findByParticipantsId(@Param("userId") Long userId);

    List<Examen> findAllByParticipants(OurUsers ourUsers);


    List<Examen> findByDate(LocalDate dateCible);

    List<Examen> findByCourseId(Long idFormation);
}


