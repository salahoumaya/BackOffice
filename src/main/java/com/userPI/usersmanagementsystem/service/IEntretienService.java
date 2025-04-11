package com.userPI.usersmanagementsystem.service;



import com.userPI.usersmanagementsystem.entity.Entretien;

import java.util.List;

public interface IEntretienService {

    public List<Entretien> retrieveAllEntretiens();
    public Entretien retrieveEntretien(Long entretienId);
    public Entretien addEntretien(Entretien c);
    public void removeEntretien(Long entretienId);
    public Entretien modifyEntretien(Entretien entretien);

    // Here we will add if any method calling keywords or JPQL


}
