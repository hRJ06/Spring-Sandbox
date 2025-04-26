package com.Hindol.ReviewService.Exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {
    String type, property, value;
    public ResourceNotFoundException(String type, String property, String value) {
        super("No resource found of Type : " + type + ", with " + property + ": " + value);
        this.type = type;
        this.property = property;
        this.value = value;
    }
}
