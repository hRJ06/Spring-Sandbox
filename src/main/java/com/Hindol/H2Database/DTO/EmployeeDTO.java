package com.Hindol.H2Database.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDTO {
    private Integer id;
    private String name;
    private String email;
    private LocalDate dateOfJoining;

}
