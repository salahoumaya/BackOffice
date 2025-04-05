package com.userPI.usersmanagementsystem.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlanningDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Day is required")
    @FutureOrPresent(message = "Day must be today or in the future")
    @Temporal(TemporalType.DATE)
    private Date day;
    @NotNull(message = "Start time is required")
    private LocalTime startTime;
    @NotNull(message = "Start time is required")
    private LocalTime endTime;
    private String description;

    @Enumerated(EnumType.STRING)
    private DayType dayType;
}
