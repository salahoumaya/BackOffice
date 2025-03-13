package com.userPI.usersmanagementsystem.repository;


import com.userPI.usersmanagementsystem.entity.Candidature;
import com.userPI.usersmanagementsystem.entity.Reclamation;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    List<Reclamation> findByUser(OurUsers user);
}
