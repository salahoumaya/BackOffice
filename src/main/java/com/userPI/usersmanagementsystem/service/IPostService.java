package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.dto.PostDto;
import com.userPI.usersmanagementsystem.entity.Post;
import com.userPI.usersmanagementsystem.enums.PostCategory;

import java.util.List;

public interface IPostService {

    Post createPost(Long userId, PostDto postDto, PostCategory category);

    List<Post> getAllPosts();

    List<Post> getPostsByUser(Long userId);

    void deletePostById(Long postId);

    Post getPostById(Long postId);
}