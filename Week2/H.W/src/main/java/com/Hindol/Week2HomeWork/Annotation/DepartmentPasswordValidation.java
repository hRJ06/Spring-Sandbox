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
        validatedBy = DepartmentPasswordValidator.class
)
public @interface DepartmentPasswordValidation {
    String message() default "The password must contain at least one uppercase letter, one lowercase letter, one special character, and minimum length of 10 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
