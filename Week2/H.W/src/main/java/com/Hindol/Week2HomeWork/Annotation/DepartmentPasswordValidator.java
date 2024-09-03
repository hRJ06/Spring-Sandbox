package com.Hindol.Week2HomeWork.Annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DepartmentPasswordValidator implements ConstraintValidator<DepartmentPasswordValidation, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if(password.length() < 10) return false;
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasSpecialChar = false;
        for(char ch : password.toCharArray()) {
            if(Character.isUpperCase(ch)) hasUppercase = true;
            if(Character.isLowerCase(ch)) hasLowercase = true;
            if(!Character.isLetterOrDigit(ch)) hasSpecialChar = true;
            if(hasUppercase && hasLowercase && hasSpecialChar) {
                return true;
            }
        }
        return false;
    }
}
