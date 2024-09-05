package com.Hindol.Week3HW.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Set;

@Data
public class ProfessorDTO {
    private Long id;
    private String title;
    @JsonIgnore
    private Set<SubjectDTO> subjects;
    private Set<StudentDTO> students;
}
