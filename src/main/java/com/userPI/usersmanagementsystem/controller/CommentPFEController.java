package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.entity.PFE.CommentPFE;
import com.userPI.usersmanagementsystem.entity.SujetPfe;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import com.userPI.usersmanagementsystem.service.CommentPFEService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/api/comments")
public class CommentPFEController {
    @Autowired
     CommentPFEService commentService;

    @Autowired
     UsersRepo userRepository;

    // Retrieve all comments for a given SujetPfe by its id.
    @GetMapping("/getAll/{sujetPfeId}")
    public ResponseEntity<List<CommentPFE>> getComments(@PathVariable Integer sujetPfeId) {
        List<CommentPFE> comments = commentService.getComments(sujetPfeId);
        return ResponseEntity.ok(comments);
    }

    // Post a new comment (with an optional file attachment)
    @PostMapping("/user/add")

    public ResponseEntity<CommentPFE> postComment(
            @RequestParam Integer internshipId,
            @RequestParam String content,
            @RequestParam String senderId,
            @RequestParam String fileName,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        CommentPFE comment = new CommentPFE();

        // Associate the comment with the SujetPfe.
        SujetPfe sujet = new SujetPfe();
        sujet.setId(internshipId);
        comment.setSujetPfe(sujet);

        // Set the sender. In a production system use your security context to retrieve the actual sender.
        OurUsers sender = userRepository.findById(Integer.parseInt(senderId)).orElse(null);
        // Replace with the authenticated user's ID.
        // You can set additional fields if necessary (e.g., sender name).
        comment.setSender(sender);

        comment.setContent(content);

        // Handle file upload: convert file to byte array.
        if (file != null && !file.isEmpty()) {
            try {
                comment.setFile(file.getBytes());
                comment.setFileName(fileName);
            } catch (IOException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        comment.setTimestamp(LocalDateTime.now());

        CommentPFE savedComment = commentService.saveComment(comment);
        return ResponseEntity.ok(savedComment);
    }
    @GetMapping("/search")
    public ResponseEntity<List<CommentPFE>> searchComments(@RequestParam String keyword) {
        List<CommentPFE> comments = commentService.searchComments(keyword);
        return ResponseEntity.ok(comments);
    }
}
