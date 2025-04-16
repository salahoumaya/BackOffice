package com.userPI.usersmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.enums.ReactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReactionType type;

    private LocalDateTime reactedAt = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private OurUsers user;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = true)
    private Post post;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = true)
    private Comment comment;
}
