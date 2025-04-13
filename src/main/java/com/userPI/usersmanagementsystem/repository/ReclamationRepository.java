package com.userPI.usersmanagementsystem.repository;


import com.userPI.usersmanagementsystem.entity.Candidature;
import com.userPI.usersmanagementsystem.entity.Reclamation;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    List<Reclamation> findByUser(OurUsers user);
    List<Reclamation> findByUserAndStatusNotAndReadIsFalse(OurUsers user, String status);

    @Query("SELECT r.type, COUNT(r) FROM Reclamation r GROUP BY r.type")
    List<Object[]> countByType();

    @Query("SELECT FUNCTION('MONTH', r.creationDate), COUNT(r) FROM Reclamation r GROUP BY FUNCTION('MONTH', r.creationDate)")
    List<Object[]> countByMonth();

    @Query("SELECT r.status, COUNT(r) FROM Reclamation r GROUP BY r.status")
    List<Object[]> countByStatus();

}
