package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.dto.CommentDto;
import com.userPI.usersmanagementsystem.entity.Comment;
import com.userPI.usersmanagementsystem.entity.Post;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.repository.CommentRepository;
import com.userPI.usersmanagementsystem.repository.PostRepository;
import com.userPI.usersmanagementsystem.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService implements ICommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UsersRepo userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Comment createComment(Long userId, Long postId, CommentDto commentDto) {
        OurUsers user = userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
