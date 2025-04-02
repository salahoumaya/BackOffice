package com.userPI.usersmanagementsystem.repository;

import com.userPI.usersmanagementsystem.entity.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Integer> {

    @Query("SELECT p FROM Planning p WHERE FUNCTION('DATE', p.startDate) = FUNCTION('DATE', :startDate)")
    List<Planning> findByStartDate(Date startDate);
}
