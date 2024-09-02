package com.Hindol.H2Database.Advice;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class APIResponse<T> {
    private LocalDateTime timeStamp;
    private APIError apiError;
    private T data;
    APIResponse() {
        timeStamp = LocalDateTime.now();
    }
    APIResponse(APIError error) {
        this();
        this.apiError = error;
    }
    APIResponse(T data) {
        this();
        this.data = data;
    }
}
