package com.Hindol.Week3HW.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SubjectDTO {
    private Long id;
    private String title;
    private ProfessorDTO professor;
    @JsonIgnore
    private Set<StudentDTO> students;
}
