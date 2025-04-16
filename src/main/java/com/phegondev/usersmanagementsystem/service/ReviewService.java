package com.phegondev.usersmanagementsystem.service;

import com.phegondev.usersmanagementsystem.dto.ReviewDTO;
import com.phegondev.usersmanagementsystem.entity.Review;
import com.phegondev.usersmanagementsystem.repository.EventRepository;
import com.phegondev.usersmanagementsystem.repository.ReviewRepository;
import com.phegondev.usersmanagementsystem.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UsersRepo userRepository;

    public List<ReviewDTO> getReviewsByEventId(Long eventId) {
        return reviewRepository.findByEvent_EventId(eventId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ReviewDTO addReview(ReviewDTO dto) {
        Review review = new Review();
        review.setContent(dto.getContent());
        review.setEvent(eventRepository.findById(dto.getEventId()).orElseThrow());
        review.setUser(userRepository.findById(Math.toIntExact(dto.getUserId())).orElseThrow());

        return mapToDTO(reviewRepository.save(review));
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    private ReviewDTO mapToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setContent(review.getContent());
        dto.setUsername(review.getUser().getName());
        dto.setUserId(Long.valueOf(review.getUser().getId()));
        dto.setEventId(review.getEvent().getEventId());
        return dto;
    }
}
