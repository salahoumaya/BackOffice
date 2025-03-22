package com.userPI.usersmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Planning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private Date startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private PlanningType planningType;

    @OneToMany (cascade = CascadeType.ALL)
    private List<PlanningDay> planningDays;

    @ManyToOne
    @JsonIgnore
    private Training training;
}
