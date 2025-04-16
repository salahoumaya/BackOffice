package com.userPI.usersmanagementsystem.dto;

import com.userPI.usersmanagementsystem.entity.user.OurUsers;

public class UserMoyenneResponse {
    private Integer userId;
    private String nom;
    private String email;
    private Double moyenne;

    public UserMoyenneResponse(OurUsers user, Double moyenne) {
        this.userId = user.getId();
        this.nom = user.getName();
        this.email = user.getEmail();
        this.moyenne = moyenne;
    }

    public Integer getUserId() {
        return userId;
    }

    public UserMoyenneResponse setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getNom() {
        return nom;
    }

    public UserMoyenneResponse setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserMoyenneResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public Double getMoyenne() {
        return moyenne;
    }

    public UserMoyenneResponse setMoyenne(Double moyenne) {
        this.moyenne = moyenne;
        return this;
    }
}
