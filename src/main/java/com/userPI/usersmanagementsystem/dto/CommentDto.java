package com.userPI.usersmanagementsystem.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CommentDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;

    private UserDto user;

    private List<ReactionDto> reactions;

    public CommentDto(Long id, String content, LocalDateTime createdAt, UserDto user, List<ReactionDto> reactions) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.reactions = reactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<ReactionDto> getReactions() {
        return reactions;
    }

    public void setReactions(List<ReactionDto> reactions) {
        this.reactions = reactions;
    }
}
