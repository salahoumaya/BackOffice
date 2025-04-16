package com.phegondev.usersmanagementsystem.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long id;
    private String content;
    private String username;
    private Long eventId;
    private Long userId;
}