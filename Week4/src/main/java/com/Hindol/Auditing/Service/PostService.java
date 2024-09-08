package com.Hindol.Auditing.Service;

import com.Hindol.Auditing.DTO.PostDTO;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPosts();
    PostDTO createNewPost(PostDTO inputPost);
    PostDTO getPostById(Long postId);
    PostDTO updatePost(PostDTO inputPost, Long postId);
}
