package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.entity.PFE.CommentPFE;
import com.userPI.usersmanagementsystem.repository.CommentPFERepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentPFEService {
    @Autowired
    private CommentPFERepo commentRepository;

    public List<CommentPFE> getComments(Integer sujetPfeId) {
        return commentRepository.findBySujetPfe_IdOrderByTimestampAsc(sujetPfeId);
    }

    public CommentPFE saveComment(CommentPFE comment) {
        comment.setTimestamp(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public List<CommentPFE> searchComments(String keyword) {
        return commentRepository.findByContentContaining(keyword);

    }
}
