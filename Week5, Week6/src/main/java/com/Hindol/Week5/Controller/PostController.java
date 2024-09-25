package com.Hindol.Week5.Controller;

import com.Hindol.Week5.DTO.PostDTO;
import com.Hindol.Week5.Service.Implementation.PostServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceImplementation postServiceImplementation;

    @GetMapping
    /* @Secured({"ROLE_ADMIN", "ROLE_CREATOR"}) */
    public List<PostDTO> getAllPosts(HttpServletRequest request) {
        return postServiceImplementation.getAllPosts();
    }

    @GetMapping("/{postId}")
    /* @PreAuthorize("hasAnyRole('CREATOR', 'ADMIN') OR hasAuthority('POST_VIEW')") */
    @PreAuthorize("@postSecurity.isOwnerOfPost(#postId)")
    public PostDTO getPostById(@PathVariable Long postId) {
        return postServiceImplementation.getPostById(postId);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CREATOR', 'ADMIN')")
    public PostDTO createNewPost(@RequestBody PostDTO inputPost) {
        return postServiceImplementation.createNewPost(inputPost);
    }

    @GetMapping("/free-content")
    @PreAuthorize("@subscriptionServiceImplementation.hasAccessBasedOnPlan(principal, 'FREE')")
    public String freeContent() {
        return "This is FREE content and is available to all users.";
    }

    @GetMapping("/basic-content")
    @PreAuthorize("@subscriptionServiceImplementation.hasAccessBasedOnPlan(principal, 'BASIC')")
    public String basicContent() {
        return "This is BASIC content and is available to BASIC and PREMIUM users.";
    }

    @GetMapping("/premium-content")
    @PreAuthorize("@subscriptionServiceImplementation.hasAccessBasedOnPlan(principal, 'PREMIUM')")
    public String premiumContent() {
        return "This is PREMIUM content and is available to only PREMIUM users.";
    }
}
