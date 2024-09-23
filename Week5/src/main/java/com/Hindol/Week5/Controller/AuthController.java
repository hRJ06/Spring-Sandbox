package com.Hindol.Week5.Controller;

import com.Hindol.Week5.DTO.LoginDTO;
import com.Hindol.Week5.DTO.LoginResponseDTO;
import com.Hindol.Week5.DTO.SignUpDTO;
import com.Hindol.Week5.DTO.UserDTO;
import com.Hindol.Week5.Service.Implementation.AuthServiceImplementation;
import com.Hindol.Week5.Service.Implementation.UserServiceImplementation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Value("${deploy.env}")
    private String deployEnv;
    private final UserServiceImplementation userServiceImplementation;
    private final AuthServiceImplementation authServiceImplementation;
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        UserDTO userDTO = userServiceImplementation.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        LoginResponseDTO loginResponseDTO = authServiceImplementation.login(loginDTO);

        Cookie cookie = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies()).filter(cookie -> "refreshToken".equals(cookie.getName())).findFirst().map(Cookie::getValue).orElseThrow(() -> new AuthenticationServiceException("Refresh Token not found inside Cookie"));
        LoginResponseDTO loginResponseDTO = authServiceImplementation.refreshToken(refreshToken);
        return ResponseEntity.ok(loginResponseDTO);
    }
}
