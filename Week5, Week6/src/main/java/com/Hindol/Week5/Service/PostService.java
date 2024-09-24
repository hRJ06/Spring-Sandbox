package com.Hindol.Week5.Service;

import com.Hindol.Week5.DTO.PostDTO;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPosts();
    PostDTO createNewPost(PostDTO inputPost);
    PostDTO getPostById(Long postId);
}
