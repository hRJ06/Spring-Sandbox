package com.Hindol.Week3HW.Advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class APIResponse<T> {
    @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    public LocalDateTime timeStamp;
    public APIError apiError;
    public T data;
    public APIResponse() {
        timeStamp = LocalDateTime.now();
    }
    public APIResponse(APIError apiError) {
        this();
        this.apiError = apiError;
    }
    public APIResponse(T data) {
        this();
        this.data = data;
    }
}
