package com.userPI.usersmanagementsystem.dto;

import com.userPI.usersmanagementsystem.enums.ReactionType;

import java.time.LocalDateTime;

public class ReactionDto {
    private Long id;
    private ReactionType type;
    private LocalDateTime reactedAt;

    public ReactionDto(Long id, ReactionType type, LocalDateTime reactedAt) {
        this.id = id;
        this.type = type;
        this.reactedAt = reactedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReactionType getType() {
        return type;
    }

    public void setType(ReactionType type) {
        this.type = type;
    }


    public LocalDateTime getReactedAt() {
        return reactedAt;
    }

    public void setReactedAt(LocalDateTime reactedAt) {
        this.reactedAt = reactedAt;
    }
}
