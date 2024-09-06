package com.Hindol.Week3HW.DTO;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class AdmissionRecordDTO {
    private Long id;
    @Min(value = 1000, message = "The minimum fees of a student admission is 1000")
    private Integer fees;
    private StudentDTO student;
}
