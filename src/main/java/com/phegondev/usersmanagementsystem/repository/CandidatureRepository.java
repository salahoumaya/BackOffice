package com.phegondev.usersmanagementsystem.repository;


import com.phegondev.usersmanagementsystem.entity.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CandidatureRepository extends JpaRepository<Candidature, Long> {


}
