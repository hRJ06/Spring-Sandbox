package com.Hindol.Week5.Handler;

import com.Hindol.Week5.Entity.UserEntity;
import com.Hindol.Week5.Service.Implementation.JWTServiceImplementation;
import com.Hindol.Week5.Service.Implementation.UserServiceImplementation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserServiceImplementation userServiceImplementation;
    private final JWTServiceImplementation jwtServiceImplementation;

    @Value("${deploy.env}")
    private String deployEnv;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User user = (DefaultOAuth2User) token.getPrincipal();

        String email = user.getAttribute("email");
        String name = user.getAttribute("name");

        UserEntity userEntity = userServiceImplementation.getUserByEmail(email);
        if(userEntity == null) {
            UserEntity newUser = UserEntity.builder()
                    .email(email)
                    .name(name)
                    .build();
            userEntity = userServiceImplementation.save(newUser);
        }
        String refreshToken = jwtServiceImplementation.generateRefreshToken(userEntity);
        String accessToken = jwtServiceImplementation.generateAccessToken(userEntity);
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);

        String frontEndURL = "http://localhost:8080/home.html?token=" + accessToken;
        /* getRedirectStrategy().sendRedirect(request,response,frontEndURL); */
        response.sendRedirect(frontEndURL);
    }
}
