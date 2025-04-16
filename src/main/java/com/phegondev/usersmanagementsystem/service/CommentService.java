package com.phegondev.usersmanagementsystem.service;

import com.phegondev.usersmanagementsystem.entity.Comment;
import com.phegondev.usersmanagementsystem.repository.CommentRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getComments(Integer sujetPfeId) {
        return commentRepository.findBySujetPfe_IdOrderByTimestampAsc(sujetPfeId);
    }

    public Comment saveComment(Comment comment) {
        comment.setTimestamp(LocalDateTime.now());
        return commentRepository.save(comment);
    }
}