package com.userPI.usersmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SujetPfe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 3, max = 100, message = "Le titre doit avoir entre 3 et 100 caractères")
    private String titre;

    @NotBlank(message = "La description est obligatoire")
    @Size(min = 10, max = 500, message = "La description doit avoir entre 10 et 500 caractères")
    private String description;

    @NotBlank(message = "La technologie est obligatoire")
    private String technologie;
    private String image;
    private String rapport;
    @Enumerated(EnumType.STRING)
    private DemandeStatus demandeStatus;


    @ManyToOne
    private OurUsers moderator;

    @JsonIgnore
    @OneToOne
    private OurUsers userAttribue;
    @JsonIgnore
    @ManyToMany()
    private List<OurUsers> demandeurs;


}
