package com.Hindol.BookService.Advice;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class APIError {
    private LocalDateTime timeStamp;
    private String error;
    private HttpStatus status;
    public APIError() {
        this.timeStamp = LocalDateTime.now();
    }
    public APIError(String error, HttpStatus status) {
        this();
        this.error = error;
        this.status = status;
    }
}
