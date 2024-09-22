package com.Hindol.Week3HW.Service.Implementation;

import com.Hindol.Week3HW.DTO.LoginDTO;
import com.Hindol.Week3HW.Entity.UserEntity;
import com.Hindol.Week3HW.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTServiceImplementation jwtServiceImplementation;
    private final SessionServiceImplementation sessionServiceImplementation;
    @Override
    public String generateToken(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        String token = jwtServiceImplementation.generateToken(userEntity);
        sessionServiceImplementation.generateNewSession(userEntity, token);
        return token;
    }
}
