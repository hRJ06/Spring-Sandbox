package com.Hindol.H2Database.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDTO {
    private Integer id;
    private String name;
    private String email;
    private LocalDate dateOfJoining;
    @JsonProperty("active")
    private Boolean isActive;

}
