package com.userPI.usersmanagementsystem.repository;

import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Integer> {
    List<Training> findByTrainer(OurUsers trainer);

    @Query(value = "SELECT * FROM Training t WHERE t.id NOT IN (SELECT training_id FROM training_users WHERE users_id = :studentId)", nativeQuery = true)
    List<Training> findForStudent(@Param("studentId") int studentId);

}
