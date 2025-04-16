package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.dto.CommentDto;
import com.userPI.usersmanagementsystem.entity.Comment;

import java.util.List;

public interface ICommentService {

    Comment createComment(Long userId, Long postId, CommentDto commentDto);

    List<Comment> getCommentsByPostId(Long postId);
}