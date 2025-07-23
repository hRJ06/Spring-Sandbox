package com.Hindol.Library_Service.Service;

import com.Hindol.Library_Service.DTO.UserDTO;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    UserDTO rentBook(Long userId, Long bookId);
    UserDTO getProfile(Long userId);
}
