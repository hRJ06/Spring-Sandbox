package com.Hindol.Week5.Controller;

import com.Hindol.Week5.DTO.PostDTO;
import com.Hindol.Week5.Service.Implementation.PostServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceImplementation postServiceImplementation;

    @GetMapping
    public List<PostDTO> getAllPosts(HttpServletRequest request) {
        return postServiceImplementation.getAllPosts();
    }

    @GetMapping("/{postId}")
    public PostDTO getPostById(@PathVariable Long postId) {
        return postServiceImplementation.getPostById(postId);
    }

    @PostMapping
    public PostDTO createNewPost(@RequestBody PostDTO inputPost) {
        return postServiceImplementation.createNewPost(inputPost);
    }
}
