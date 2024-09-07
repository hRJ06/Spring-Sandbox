package com.Hindol.Week3HW.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SubjectDTO {
    private Long id;
    private String title;
    private ProfessorDTO professor;
    private Set<StudentDTO> students;
}
