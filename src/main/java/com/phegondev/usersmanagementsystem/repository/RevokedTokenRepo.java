package com.phegondev.usersmanagementsystem.repository;

import com.phegondev.usersmanagementsystem.entity.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RevokedTokenRepo extends JpaRepository<RevokedToken, Integer> {
    Optional<RevokedToken> findByToken(String token);
}
