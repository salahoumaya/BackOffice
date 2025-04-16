package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.dto.PostDto;
import com.userPI.usersmanagementsystem.entity.Post;
import com.userPI.usersmanagementsystem.enums.PostCategory;
import com.userPI.usersmanagementsystem.service.IPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestParam Long userId, @RequestBody PostDto postDto) {
        PostCategory category = postDto.getCategory() != null ? PostCategory.valueOf(postDto.getCategory()) : PostCategory.GENERAL;

        Post createdPost = postService.createPost(userId, postDto, category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @GetMapping("/posts-by-id")
    public ResponseEntity<List<Post>> getPostsByUserId(@RequestParam Long userId) {
        List<Post> myPosts = postService.getPostsByUser(userId);
        return ResponseEntity.ok(myPosts);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(post); // 204 No Content
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePostById(postId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
