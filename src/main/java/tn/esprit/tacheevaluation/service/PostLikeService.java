package tn.esprit.tacheevaluation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tacheevaluation.entity.PostLike;
import tn.esprit.tacheevaluation.repository.PostLikeRepository;

import java.util.List;

@Service
public class PostLikeService {
    @Autowired
    private PostLikeRepository postLikeRepo;

    public List<PostLike> getAllLikes() { return postLikeRepo.findAll(); }

    public PostLike createLike(PostLike postLike) {
        if (!postLikeRepo.existsByUserAndComment(postLike.getUser(), postLike.getComment())) {
            return postLikeRepo.save(postLike);
        }
        return null;
    }

    public void deleteLike(Long id) {
        postLikeRepo.deleteById(id);
    }
}
