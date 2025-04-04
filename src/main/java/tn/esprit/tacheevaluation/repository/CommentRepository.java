package tn.esprit.tacheevaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tacheevaluation.entity.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long> {
}
