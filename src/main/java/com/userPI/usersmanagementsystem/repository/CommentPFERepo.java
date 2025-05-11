package com.userPI.usersmanagementsystem.repository;

import com.userPI.usersmanagementsystem.entity.Candidature;
import com.userPI.usersmanagementsystem.entity.PFE.CommentPFE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentPFERepo extends JpaRepository<CommentPFE, Integer> {
    List<CommentPFE> findBySujetPfe_IdOrderByTimestampAsc(Integer internshipId);
    List<CommentPFE> findByContentContaining(String keyword);
}
