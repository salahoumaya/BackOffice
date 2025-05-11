package com.userPI.usersmanagementsystem.service.Event;

import com.userPI.usersmanagementsystem.dto.RatingDTO;

public interface IRatingService {
    void rateEvent(RatingDTO ratingDTO);
    double getAverageRatingForEvent(Long eventId);
}