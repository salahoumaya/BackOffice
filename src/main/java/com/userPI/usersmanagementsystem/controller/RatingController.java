package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.dto.RatingDTO;
import com.userPI.usersmanagementsystem.service.Event.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rating")
@CrossOrigin(origins = "http://localhost:4200")
public class RatingController {
    @Autowired
    private IRatingService ratingService;

    @PostMapping("/rate")
    public ResponseEntity<?> rateEvent(@RequestBody RatingDTO ratingDTO) {
        ratingService.rateEvent(ratingDTO);
        return ResponseEntity.ok(Map.of("message", "Note enregistrée"));
    }

    @GetMapping("/rating/{eventId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long eventId) {
        double avg = ratingService.getAverageRatingForEvent(eventId);
        return ResponseEntity.ok(avg);
    }
}