package com.Hindol.Week3HW.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Set;

@Data
public class SubjectDTO {
    private Long id;
    private String title;
    private ProfessorDTO professorDTO;
    @JsonIgnore
    private Set<StudentDTO> students;
}
