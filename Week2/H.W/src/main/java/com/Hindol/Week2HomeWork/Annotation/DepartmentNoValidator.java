package com.Hindol.Week2HomeWork.Annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DepartmentNoValidator implements ConstraintValidator<DepartmentNoValidation, Long> {
    @Override
    public boolean isValid(Long n, ConstraintValidatorContext constraintValidatorContext) {
        if(n <= 1) return false;
        for(int i = 2; i <= Math.sqrt(n); i++) {
            if(!(n % i != 0)) return false;
        }
        return true;
    }
}
