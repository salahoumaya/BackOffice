package com.phegondev.usersmanagementsystem.service;

import com.phegondev.usersmanagementsystem.dto.RatingDTO;

public interface IRatingService {
    void rateEvent(RatingDTO ratingDTO);
    double getAverageRatingForEvent(Long eventId);
}
