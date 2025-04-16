package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.dto.CommentDto;
import com.userPI.usersmanagementsystem.entity.Comment;
import com.userPI.usersmanagementsystem.service.ICommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestParam Long userId,
            @RequestParam Long postId,
            @RequestBody CommentDto commentDto) {

        Comment createdComment = commentService.createComment(userId, postId, commentDto);
        return ResponseEntity.status(HttpStatus.OK).body(createdComment);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}
