package com.userPI.usersmanagementsystem.repository;

import com.userPI.usersmanagementsystem.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByEvent_EventId(Long eventId);
}