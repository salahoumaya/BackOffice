package com.userPI.usersmanagementsystem.repository;

import com.userPI.usersmanagementsystem.entity.Reaction;
import com.userPI.usersmanagementsystem.enums.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    Optional<Reaction> findByUserIdAndPostId(Long userId, Long postId);

    Optional<Reaction> findByUserIdAndCommentId(Long userId, Long commentId);

    List<Reaction> findByPostId(Long postId);

    long countByPostIdAndType(Long postId, ReactionType type);

    Optional<Reaction> findByUserIdAndPostIdAndCommentId(Long userId, Long postId, Long commentId);

    List<Reaction> findByCommentId(Long commentId);

    List<Reaction> findByPostIdAndCommentId(Long postId, Long commentId);
}

