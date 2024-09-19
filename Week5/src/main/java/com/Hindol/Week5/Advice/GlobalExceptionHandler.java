package com.Hindol.Week5.Advice;

import com.Hindol.Week5.Exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        APIError apiError = new APIError(resourceNotFoundException.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<APIError>(apiError, HttpStatus.NOT_FOUND);
    }
}
