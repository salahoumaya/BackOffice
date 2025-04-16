package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.entity.Comment;
import com.userPI.usersmanagementsystem.entity.Post;
import com.userPI.usersmanagementsystem.entity.Reaction;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.enums.ReactionType;
import com.userPI.usersmanagementsystem.repository.CommentRepository;
import com.userPI.usersmanagementsystem.repository.PostRepository;
import com.userPI.usersmanagementsystem.repository.ReactionRepository;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReactionService implements IReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private UsersRepo userRepository;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Reaction addReactionToPost(Long userId, Long postId, ReactionType type) {
        OurUsers user = userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Optional<Reaction> existingReaction = reactionRepository.findByUserIdAndPostIdAndCommentId(userId, postId, null);

        if (existingReaction.isPresent()) {
            Reaction reaction = existingReaction.get();
            reaction.setType(type); // update the type (switch like ↔ dislike)
            return reactionRepository.save(reaction);
        }

        Reaction reaction = new Reaction();
        reaction.setUser(user);
        reaction.setPost(post);
        reaction.setType(type);
        reaction.setReactedAt(LocalDateTime.now());

        return reactionRepository.save(reaction);
    }


    @Override
    public Reaction addReactionToComment(Long userId, Long commentId, ReactionType type) {
        OurUsers user = userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        Optional<Reaction> existingReaction = reactionRepository.findByUserIdAndPostIdAndCommentId(userId, comment.getPost().getId(), commentId);

        if (existingReaction.isPresent()) {
            Reaction reaction = existingReaction.get();
            reaction.setPost(comment.getPost());
            reaction.setType(type); // update the type (switch like ↔ dislike)
            return reactionRepository.save(reaction);
        }

        Reaction reaction = new Reaction();
        reaction.setUser(user);
        reaction.setComment(comment);
        reaction.setPost(comment.getPost());
        reaction.setType(type);
        reaction.setReactedAt(LocalDateTime.now());

        return reactionRepository.save(reaction);
    }

    @Override
    public void removeReaction(Long userId, Long postId) {
        reactionRepository.findByUserIdAndPostId(userId, postId)
                .ifPresent(reactionRepository::delete);
    }

    @Override
    public long countReactionsByType(Long postId, ReactionType type) {
        return reactionRepository.countByPostIdAndType(postId, type);
    }

    @Override
    public List<OurUsers> getUsersByReaction(Long postId, ReactionType type) {
        return reactionRepository.findByPostId(postId).stream()
                .filter(r -> r.getType() == type)
                .map(Reaction::getUser)
                .toList();
    }

    @Override
    public Map<ReactionType, Long> countReactionsGroupedByType(Long postId) {
        List<Reaction> byPostIdAndCommentId = reactionRepository.findByPostIdAndCommentId(postId, null);
        return byPostIdAndCommentId
                .stream()
                .collect(Collectors.groupingBy(Reaction::getType, Collectors.counting()));
    }


    @Override
    public Map<ReactionType, Long> countReactionsGroupedByTypeForComment(Long commentId) {
        return reactionRepository.findByCommentId(commentId)
                .stream()
                .collect(Collectors.groupingBy(Reaction::getType, Collectors.counting()));
    }

    @Override
    public List<Map<String, String>> getUsersByReactionForComment(Long commentId) {
        List<Reaction> reactions = reactionRepository.findByCommentId(commentId);
        return reactions.stream()
                .map(r -> Map.of(
                        "name", r.getUser().getName(),
                        "type", r.getType().name()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, String>> getUsersWithReactionsForPost(Long postId) {
        List<Reaction> reactions = reactionRepository.findByPostIdAndCommentId(postId, null);
        return reactions.stream()
                .map(r -> Map.of(
                        "name", r.getUser().getName(),
                        "type", r.getType().name()
                ))
                .collect(Collectors.toList());
    }
}
