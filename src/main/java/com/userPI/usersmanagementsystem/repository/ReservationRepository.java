package com.userPI.usersmanagementsystem.repository;


import com.userPI.usersmanagementsystem.entity.Event;
import com.userPI.usersmanagementsystem.entity.Reservation;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(OurUsers user);
    List<Reservation> findByEvent(Event event);
    @Query("SELECT r.event FROM Reservation r WHERE r.user.id = :userId")
    List<Event> findEventsByUserId(@Param("userId") Long userId);}


