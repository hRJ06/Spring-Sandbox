package com.Hindol.Library_Service.Controller;

import com.Hindol.Library_Service.DTO.UserDTO;
import com.Hindol.Library_Service.Entity.UserEntity;
import com.Hindol.Library_Service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @PutMapping("/rent")
    public ResponseEntity<?> rentBook(@RequestParam("userId") Long userId, @RequestParam("bookId") Long bookId) {
        userService.rentBook(userId, bookId);
        return ResponseEntity.ok().build();
    }
}
