package com.Hindol.Week5.Service.Implementation;

import com.Hindol.Week5.DTO.LoginDTO;
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

    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return jwtServiceImplementation.generateToken(user);
    }
}
