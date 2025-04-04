package tn.esprit.tacheevaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tacheevaluation.entity.Comment;
import tn.esprit.tacheevaluation.entity.PostLike;
import tn.esprit.tacheevaluation.entity.OurUsers;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserAndComment(OurUsers user, Comment comment);
}
