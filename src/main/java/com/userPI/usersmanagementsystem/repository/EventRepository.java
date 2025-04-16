package com.userPI.usersmanagementsystem.repository;


import com.userPI.usersmanagementsystem.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByScheduledAtAfter(LocalDateTime currentDateTime);
}
