package tn.esprit.tacheevaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tacheevaluation.entity.Comment;
import tn.esprit.tacheevaluation.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Comment> getAll() { return commentService.getAllComments(); }

    @GetMapping("/{id}")
    public Comment get(@PathVariable Long id) { return commentService.getComment(id); }

    @PostMapping
    public Comment create(@RequestBody Comment comment) { return commentService.createComment(comment); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { commentService.deleteComment(id); }
}