package tn.esprit.tacheevaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tacheevaluation.entity.Post;
import tn.esprit.tacheevaluation.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAll() { return postService.getAllPosts(); }

    @GetMapping("/{id}")
    public Post get(@PathVariable Long id) { return postService.getPost(id); }

    @PostMapping
    public Post create(@RequestBody Post post) { return postService.createPost(post); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { postService.deletePost(id); }
}