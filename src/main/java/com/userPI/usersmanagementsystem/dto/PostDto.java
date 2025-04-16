package com.userPI.usersmanagementsystem.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PostDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    private String category;

    private UserDto author;

    private List<CommentDto> comments;

    private List<ReactionDto> reactions;

    // Getters and Setters

    public PostDto(Long id, String title, String content, LocalDateTime createdAt, String category, UserDto author, List<CommentDto> comments, List<ReactionDto> reactions) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.category = category;
        this.author = author;
        this.comments = comments;
        this.reactions = reactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public List<ReactionDto> getReactions() {
        return reactions;
    }

    public void setReactions(List<ReactionDto> reactions) {
        this.reactions = reactions;
    }
}
