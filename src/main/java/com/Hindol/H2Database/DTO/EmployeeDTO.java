package com.Hindol.H2Database.DTO;

import com.Hindol.H2Database.Annotation.EmployeeRoleValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDTO {
    private Integer id;
    @NotBlank(message = "The name of employee cannot be blank")
    @Size(min = 3, max = 100, message = "Number of characters in the name should be in the range [3, 10]")
    private String name;
    @Email(message = "The email should be a valid email")
    private String email;
    @NotNull(message = "The age of employee cannot be null")
    @Min(value = 18, message = "The minimum age of employee should be 18")
    @Max(value = 80, message = "The maximum age of employee should be 80")
    private Integer age;
    /* @Pattern(regexp = "^ADMIN|USER$", message = "The Role of employee can be ADMIN or USER") */
    @EmployeeRoleValidation
    private String role;
    @NotNull(message = "The age of employee cannot be null")
    @Positive(message = "The salary of an employee cannot be negative")
    @Digits(integer = 6, fraction = 2, message = "The salary of an employee should be in the form XXXXXX.YY")
    @DecimalMax(value = "100000.99")
    @DecimalMin(value = "100.50")
    private Double salary;
    @PastOrPresent(message = "The Date of Joining of employee cannot be in future")
    private LocalDate dateOfJoining;
    @JsonProperty("active")
    @AssertTrue(message = "Employee should be active")
    private Boolean isActive;

}
