package com.userPI.usersmanagementsystem.repository;

import com.userPI.usersmanagementsystem.entity.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RevokedTokenRepo extends JpaRepository<RevokedToken, Integer> {
    Optional<RevokedToken> findByToken(String token);
}
