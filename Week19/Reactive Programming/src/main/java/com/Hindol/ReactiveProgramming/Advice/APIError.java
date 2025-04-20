package com.Hindol.ReactiveProgramming.Advice;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
public class APIError {
    private LocalDateTime timeStamp;
    private String error;
    private HttpStatusCode statusCode;
    public APIError() {
        this.timeStamp = LocalDateTime.now();
    }
    public APIError(String error, HttpStatusCode statusCode) {
        this();
        this.error = error;
        this.statusCode = statusCode;
    }
}
