package tn.esprit.tacheevaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tacheevaluation.entity.PostLike;
import tn.esprit.tacheevaluation.service.PostLikeService;

import java.util.List;

@RestController
@RequestMapping("/post-likes")
public class PostLikeController {
    @Autowired
    private PostLikeService postLikeService;

    @GetMapping
    public List<PostLike> getAll() {
        return postLikeService.getAllLikes();
    }

    @PostMapping
    public PostLike create(@RequestBody PostLike postLike) {
        return postLikeService.createLike(postLike);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        postLikeService.deleteLike(id);
    }
}
