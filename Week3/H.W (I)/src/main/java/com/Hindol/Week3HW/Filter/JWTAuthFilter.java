package com.Hindol.Week3HW.Filter;

import com.Hindol.Week3HW.Entity.UserEntity;
import com.Hindol.Week3HW.Service.Implementation.JWTServiceImplementation;
import com.Hindol.Week3HW.Service.Implementation.SessionServiceImplementation;
import com.Hindol.Week3HW.Service.Implementation.UserServiceImplementation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTServiceImplementation jwtServiceImplementation;
    private final UserServiceImplementation userServiceImplementation;

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String requestTokenHeader = request.getHeader("Authorization");
            if(Objects.isNull(requestTokenHeader)  || !requestTokenHeader.startsWith("Bearer")) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = requestTokenHeader.split("Bearer ")[1];
            Long userId = jwtServiceImplementation.getUserIdFromToken(token);

            if(userId != null && Objects.isNull(SecurityContextHolder.getContext().getAuthentication()) && jwtServiceImplementation.validateSession(token))  {
                UserEntity userEntity = userServiceImplementation.getUserById(userId);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEntity, null, null);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        }
        catch (Exception ex) {
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
