package com.userPI.usersmanagementsystem.repository;

import com.userPI.usersmanagementsystem.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAuthorId(Long id);
}