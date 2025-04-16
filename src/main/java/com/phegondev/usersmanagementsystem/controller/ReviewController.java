package com.phegondev.usersmanagementsystem.controller;

import com.phegondev.usersmanagementsystem.dto.ReviewDTO;
import com.phegondev.usersmanagementsystem.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/event/{eventId}")
    public List<ReviewDTO> getReviews(@PathVariable Long eventId) {
        return reviewService.getReviewsByEventId(eventId);
    }

    @PostMapping
    public ReviewDTO addReview(@RequestBody ReviewDTO dto) {
        return reviewService.addReview(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
