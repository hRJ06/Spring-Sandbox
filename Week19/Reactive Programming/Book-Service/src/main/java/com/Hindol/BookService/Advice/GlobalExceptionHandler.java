package com.Hindol.BookService.Advice;

import com.Hindol.BookService.Exception.ClientException;
import com.Hindol.BookService.Exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<APIError>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("No resource found of Type : {}, with {} : {}", ex.getType(), ex.getProperty(), ex.getValue());
        APIError apiError = new APIError(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        return Mono.just(new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(ClientException.class)
    public Mono<ResponseEntity<APIError>> handleClientException(ClientException ex) {
        log.error(ex.getMessage());
        APIError apiError = new APIError(ex.getLocalizedMessage(), ex.getHttpStatus());
        return Mono.just(new ResponseEntity<>(apiError, ex.getHttpStatus()));
    }

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<APIError>> handleInternalServerError(RuntimeException ex) {
        log.error(ex.getMessage());
        APIError apiError = new APIError(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return Mono.just(new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
