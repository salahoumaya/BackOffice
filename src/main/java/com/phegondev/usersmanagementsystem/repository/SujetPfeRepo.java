package com.phegondev.usersmanagementsystem.repository;

import com.phegondev.usersmanagementsystem.entity.OurUsers;
import com.phegondev.usersmanagementsystem.entity.SujetPfe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SujetPfeRepo extends JpaRepository<SujetPfe, Integer> {
    List<SujetPfe> findByUserAttribueId(Integer userId);
    List<SujetPfe> findByDemandeurs_Id(Integer userId);
    List<SujetPfe> findByModeratorId(Integer moderatorId);
    @Query("SELECT COUNT(s) FROM SujetPfe s WHERE s.userAttribue IS NOT NULL")
    long countSujetsAttribues();

    @Query("SELECT COUNT(s) FROM SujetPfe s")
    long countTotalSujets();

    List<SujetPfe> findSujetPfeByUserAttribue_Id(Integer id);

    List<SujetPfe> findAllByDemandeursContains(OurUsers users);
    @Query("SELECT s.userAttribue FROM SujetPfe s WHERE s.id = :sujetPfeId")
    Optional<OurUsers> findUserAttribueBySujetPfeId(@Param("sujetPfeId") Integer sujetPfeId);

}
