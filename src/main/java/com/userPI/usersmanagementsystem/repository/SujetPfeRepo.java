package com.userPI.usersmanagementsystem.repository;


import com.userPI.usersmanagementsystem.entity.SujetPfe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SujetPfeRepo extends JpaRepository<SujetPfe, Integer> {
    List<SujetPfe> findByUserAttribueId(Integer userId);
    List<SujetPfe> findByDemandeurs_Id(Integer userId);
    List<SujetPfe> findByModeratorId(Integer moderatorId);
    @Query("SELECT COUNT(s) FROM SujetPfe s WHERE s.userAttribue IS NOT NULL")
    long countSujetsAttribues();

    @Query("SELECT COUNT(s) FROM SujetPfe s")
    long countTotalSujets();

}
