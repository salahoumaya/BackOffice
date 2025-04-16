package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.dto.ReactionDto;
import com.userPI.usersmanagementsystem.entity.Reaction;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.enums.ReactionType;
import com.userPI.usersmanagementsystem.service.IReactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reactions")
public class ReactionController {

    private final IReactionService reactionService;

    public ReactionController(IReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @PostMapping("/post")
    public ResponseEntity<Reaction> reactToPost(
            @RequestParam Long userId,
            @RequestParam Long postId,
            @RequestBody ReactionDto reactionDto) {

        Reaction reaction = reactionService.addReactionToPost(userId, postId, reactionDto.getType());
        return ResponseEntity.ok(reaction);
    }
    @PostMapping("/comment")
    public ResponseEntity<Reaction> reactToComment(
            @RequestParam Long userId,
            @RequestParam Long commentId,
            @RequestBody ReactionDto reactionDto) {

        Reaction reaction = reactionService.addReactionToComment(userId, commentId, reactionDto.getType());
        return ResponseEntity.ok(reaction);
    }
    @DeleteMapping
    public ResponseEntity<Void> removeReaction(@RequestParam Long userId, @RequestParam Long postId) {
        reactionService.removeReaction(userId, postId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/users")
    public ResponseEntity<List<OurUsers>> getUsersByReaction(
            @RequestParam Long postId,
            @RequestParam ReactionType type) {
        return ResponseEntity.ok(reactionService.getUsersByReaction(postId, type));
    }

    @GetMapping("/post-reactions")
    public ResponseEntity<Map<ReactionType, Long>> getPostReactions(@RequestParam Long postId) {
        return ResponseEntity.ok(reactionService.countReactionsGroupedByType(postId));
    }
    @GetMapping("/comment-reactions")
    public ResponseEntity<Map<ReactionType, Long>> getCommentReactions(@RequestParam Long commentId) {
        return ResponseEntity.ok(reactionService.countReactionsGroupedByTypeForComment(commentId));
    }
    @GetMapping("/comment-users")
    public ResponseEntity<List<Map<String, String>>> getUsersByCommentReaction(
            @RequestParam Long commentId) {
        return ResponseEntity.ok(reactionService.getUsersByReactionForComment(commentId));
    }
    @GetMapping("/post-users")
    public ResponseEntity<List<Map<String, String>>> getPostReactionsWithUsers(@RequestParam Long postId) {
        return ResponseEntity.ok(reactionService.getUsersWithReactionsForPost(postId));
    }

}
