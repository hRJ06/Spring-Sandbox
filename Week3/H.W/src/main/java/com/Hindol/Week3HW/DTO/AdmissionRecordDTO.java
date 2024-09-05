package com.Hindol.Week3HW.DTO;

import lombok.Data;

@Data
public class AdmissionRecordDTO {
    private Long id;
    private Integer fees;
    private StudentDTO student;
}
