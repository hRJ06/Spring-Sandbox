package com.Hindol.Week2HomeWork.Annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(
        validatedBy = DepartmentNoValidator.class
)
public @interface DepartmentNoValidation {
    String message() default "The Department No must be a prime number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
