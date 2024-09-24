package com.Hindol.Week5.Util;

import com.Hindol.Week5.DTO.PostDTO;
import com.Hindol.Week5.Entity.UserEntity;
import com.Hindol.Week5.Service.Implementation.PostServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {
    private final PostServiceImplementation postServiceImplementation;
    public boolean isOwnerOfPost(Long postId) {
        PostDTO postDTO = postServiceImplementation.getPostById(postId);
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return postDTO.getAuthor().getId().equals(user.getId());
    }
}
