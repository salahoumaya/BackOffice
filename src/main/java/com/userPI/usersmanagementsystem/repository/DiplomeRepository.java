package com.userPI.usersmanagementsystem.repository;


import com.userPI.usersmanagementsystem.entity.Diplome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DiplomeRepository extends JpaRepository<Diplome, Long> {
}
