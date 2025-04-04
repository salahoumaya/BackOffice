package tn.esprit.tacheevaluation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tacheevaluation.entity.Post;
import tn.esprit.tacheevaluation.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepo;

    public List<Post> getAllPosts() { return postRepo.findAll(); }
    public Post getPost(Long id) { return postRepo.findById(id).orElse(null); }
    public Post createPost(Post post) { return postRepo.save(post); }
    public void deletePost(Long id) { postRepo.deleteById(id); }
}