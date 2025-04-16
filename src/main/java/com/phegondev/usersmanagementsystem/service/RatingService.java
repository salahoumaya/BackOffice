package com.phegondev.usersmanagementsystem.service;

import com.phegondev.usersmanagementsystem.dto.RatingDTO;
import com.phegondev.usersmanagementsystem.entity.Event;
import com.phegondev.usersmanagementsystem.entity.OurUsers;
import com.phegondev.usersmanagementsystem.entity.Rating;
import com.phegondev.usersmanagementsystem.repository.EventRepository;
import com.phegondev.usersmanagementsystem.repository.RatingRepository;
import com.phegondev.usersmanagementsystem.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService implements IRatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UsersRepo userRepository;

    @Override
    public void rateEvent(RatingDTO ratingDTO) {
        Optional<Rating> existingRating = ratingRepository.findByUserIdAndEvent_EventId(
                Math.toIntExact(ratingDTO.getUserId()), ratingDTO.getEventId()
        );

        Rating rating = existingRating.orElseGet(Rating::new);

        OurUsers user = userRepository.findById(Math.toIntExact(ratingDTO.getUserId()))
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        Event event = eventRepository.findById(ratingDTO.getEventId())
                .orElseThrow(() -> new RuntimeException("Événement introuvable"));

        rating.setUser(user);
        rating.setEvent(event);
        rating.setStars(ratingDTO.getStars());
        rating.setComment(ratingDTO.getComment());

        ratingRepository.save(rating);
    }

    @Override
    public double getAverageRatingForEvent(Long eventId) {
        List<Rating> ratings = ratingRepository.findByEvent_EventId(eventId);
        if (ratings.isEmpty()) return 0.0;

        return ratings.stream()
                .mapToInt(Rating::getStars)
                .average()
                .orElse(0.0);
    }
}
