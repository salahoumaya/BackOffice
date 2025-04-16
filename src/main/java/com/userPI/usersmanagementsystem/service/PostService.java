package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.dto.PostDto;
import com.userPI.usersmanagementsystem.entity.Post;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.enums.PostCategory;
import com.userPI.usersmanagementsystem.repository.PostRepository;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsersRepo userRepository;

    @Override
    public Post createPost(Long userId, PostDto postDto, PostCategory category) {
        OurUsers author = userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCategory(category);
        post.setCreatedAt(LocalDateTime.now());
        post.setAuthor(author);

        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getPostsByUser(Long userId) {
        return postRepository.findAllByAuthorId(userId);
    }

    public void deletePostById(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            postRepository.deleteById(postId);
        } else {
            throw new EntityNotFoundException("Post not found with ID: " + postId);
        }
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }
}
