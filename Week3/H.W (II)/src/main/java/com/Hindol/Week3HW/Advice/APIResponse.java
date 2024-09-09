package com.Hindol.Week3HW.Advice;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class APIResponse<T> {
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timeStamp;
    private APIError apiError;
    private T data;

    public APIResponse() {
        this.timeStamp = LocalDateTime.now();
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
