package com.Hindol.Week5.Service.Implementation;

import com.Hindol.Week5.DTO.LoginDTO;
import com.Hindol.Week5.DTO.LoginResponseDTO;
import com.Hindol.Week5.Entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation {
    private final AuthenticationManager authenticationManager;
    private final JWTServiceImplementation jwtServiceImplementation;
    private final UserServiceImplementation userServiceImplementation;

    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        UserEntity user = (UserEntity) authentication.getPrincipal();
        String accessToken = jwtServiceImplementation.generateAccessToken(user);
        String refreshToken = jwtServiceImplementation.generateRefreshToken(user);
        return new LoginResponseDTO(user.getId(), accessToken, refreshToken);
    }

    public LoginResponseDTO refreshToken(String refreshToken) {
        Long userId = jwtServiceImplementation.getUserIdFromToken(refreshToken);
        UserEntity userEntity = userServiceImplementation.getUserById(userId);
        String accessToken = jwtServiceImplementation.generateAccessToken(userEntity);
        return new LoginResponseDTO(userId, accessToken, refreshToken);
    }
}