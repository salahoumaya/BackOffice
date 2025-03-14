package com.userPI.usersmanagementsystem.repository;


import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<OurUsers, Integer> {

    Optional<OurUsers> findByEmail(String email);
    //khdemna biha kifch l admin yzid trainer
    @Query("SELECT u FROM OurUsers u WHERE u.role = :role")
    List<OurUsers> findByRole(@Param("role") UserRole role);




}
