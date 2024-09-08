package com.Hindol.Auditing.Advice;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class APIResponse<T> {

    private LocalDateTime timeStamp;
    private T data;
    private APIError error;

    public APIResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    public APIResponse(T data) {
        this();
        this.data = data;
    }

    public APIResponse(APIError error) {
        this();
        this.error = error;
    }
}