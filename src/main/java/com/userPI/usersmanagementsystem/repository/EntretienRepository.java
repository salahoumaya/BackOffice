package com.userPI.usersmanagementsystem.repository;

import com.userPI.usersmanagementsystem.entity.Entretien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface EntretienRepository extends JpaRepository<Entretien, Long> {

}
