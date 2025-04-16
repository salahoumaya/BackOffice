package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.entity.Reaction;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.enums.ReactionType;

import java.util.List;
import java.util.Map;

public interface IReactionService {

    Reaction addReactionToPost(Long userId, Long postId, ReactionType type);
    Reaction addReactionToComment(Long userId, Long postId, ReactionType type);

    void removeReaction(Long userId, Long postId);

    long countReactionsByType(Long postId, ReactionType type);

    List<OurUsers> getUsersByReaction(Long postId, ReactionType type);

    Map<ReactionType, Long> countReactionsGroupedByType(Long postId);

    Map<ReactionType, Long> countReactionsGroupedByTypeForComment(Long commentId);

    List<Map<String, String>> getUsersByReactionForComment(Long commentId);

    List<Map<String, String>> getUsersWithReactionsForPost(Long postId);
}
