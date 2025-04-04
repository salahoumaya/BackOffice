package tn.esprit.tacheevaluation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tacheevaluation.entity.Comment;
import tn.esprit.tacheevaluation.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepo;

    public List<Comment> getAllComments() { return commentRepo.findAll(); }
    public Comment getComment(Long id) { return commentRepo.findById(id).orElse(null); }
    public Comment createComment(Comment comment) { return commentRepo.save(comment); }
    public void deleteComment(Long id) { commentRepo.deleteById(id); }
}
