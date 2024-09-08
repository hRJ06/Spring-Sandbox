package com.Hindol.Auditing.Advice;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class APIError {
    private String error;
    private HttpStatus status;
    private List<String> subErrors;
    public APIError(String error, HttpStatus status) {
        this.error = error;
        this.status = status;
    }
}
