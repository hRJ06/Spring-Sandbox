package com.Hindol.Command.Advice;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class APIError {
    private String error;
    private HttpStatus status;
    public APIError(String error, HttpStatus status) {
        this.error = error;
        this.status = status;
    }
}
