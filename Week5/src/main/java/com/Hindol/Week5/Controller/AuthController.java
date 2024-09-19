package com.Hindol.Week5.Controller;

import com.Hindol.Week5.DTO.LoginDTO;
import com.Hindol.Week5.DTO.SignUpDTO;
import com.Hindol.Week5.DTO.UserDTO;
import com.Hindol.Week5.Service.Implementation.AuthServiceImplementation;
import com.Hindol.Week5.Service.Implementation.UserServiceImplementation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserServiceImplementation userServiceImplementation;
    private final AuthServiceImplementation authServiceImplementation;
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = userServiceImplementation.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        String token = authServiceImplementation.login(loginDTO);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(token);
    }

}
