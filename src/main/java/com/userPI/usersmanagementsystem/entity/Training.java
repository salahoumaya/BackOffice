package com.userPI.usersmanagementsystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 50, message = "Title must be between 5 and 50 characters")
    private String title;

    @NotBlank(message = "Level is required")
    private String level;

    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    private typeTraining typeTraning;


    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "training_users",
            joinColumns = @JoinColumn(name = "training_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id")
    )
    private List<OurUsers> users = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "training_users", joinColumns = @JoinColumn(name = "training_id"))
    @MapKeyColumn(name = "users_id")
    @Column(name = "subscription_date")
    private Map<Integer, LocalDateTime> subscriptionDates = new HashMap<>();

    @JsonIgnore
    @ManyToOne
    private OurUsers trainer;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<Planning> plannings = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
    private List<Diplome> diplomeList;
    public Map<Integer, LocalDateTime> getSubscriptionDates() {
        return subscriptionDates == null ? new HashMap<>() : subscriptionDates;
    }

    public void addUserToTraining(OurUsers user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        if (!users.contains(user)) {
            users.add(user);
            subscriptionDates.put(user.getId(), LocalDateTime.now());
        }
    }


}
