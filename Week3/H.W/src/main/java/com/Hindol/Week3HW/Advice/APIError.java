package com.Hindol.Week3HW.Advice;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@ToString
public class APIError {
    private HttpStatus httpStatus;
    private String message;
    private List<String> subErrors;

}
