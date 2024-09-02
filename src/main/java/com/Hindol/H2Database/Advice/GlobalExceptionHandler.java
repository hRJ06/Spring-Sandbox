package com.Hindol.H2Database.Advice;

import com.Hindol.H2Database.Exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIError> handleInputValidationError(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
        APIError apiError = APIError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("Input Validation failed").
                subErrors(errors).build();
        return new ResponseEntity<APIError>(apiError, apiError.getHttpStatus());
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> handleResourceNotFoundException(ResourceNotFoundException e) {
        APIError apiError = APIError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(e.getMessage()).build();
        return new ResponseEntity<APIError>(apiError, apiError.getHttpStatus());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> handleInternalServerError(Exception e) {
        APIError apiError = APIError.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();
        return new ResponseEntity<APIError>(apiError, apiError.getHttpStatus());
    }

}
