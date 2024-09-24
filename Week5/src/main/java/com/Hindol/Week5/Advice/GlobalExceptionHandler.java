package com.Hindol.Week5.Advice;

import com.Hindol.Week5.Exception.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        APIError apiError = new APIError(resourceNotFoundException.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<APIError>(apiError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<APIError> handleAuthenticationException(AuthenticationException authenticationException) {
        APIError apiError = new APIError(authenticationException.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<APIError>(apiError, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<APIError> handleAuthenticationException(JwtException jwtException) {
        APIError apiError = new APIError(jwtException.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<APIError>(apiError, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIError> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        APIError apiError = new APIError(accessDeniedException.getLocalizedMessage(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<APIError>(apiError, HttpStatus.FORBIDDEN);
    }
}
