package tn.esprit.tacheevaluation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tacheevaluation.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
