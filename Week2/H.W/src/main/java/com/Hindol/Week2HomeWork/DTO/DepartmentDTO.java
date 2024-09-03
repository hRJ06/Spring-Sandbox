package com.Hindol.Week2HomeWork.DTO;

import com.Hindol.Week2HomeWork.Annotation.DepartmentNoValidation;
import com.Hindol.Week2HomeWork.Annotation.DepartmentPasswordValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    private Long Id;
    @NotBlank(message = "The title of Department cannot be blank")
    @Size(min = 2, max = 100, message = "Number of characters in the title should be in the range [3, 25]")
    private String title;
    @AssertTrue(message = "A department must be active")
    private Boolean isActive;
    @PastOrPresent
    @NotNull
    private LocalDateTime createdAt;
    @DepartmentNoValidation
    private Long no;
    @DepartmentPasswordValidation
    private String password;
}
