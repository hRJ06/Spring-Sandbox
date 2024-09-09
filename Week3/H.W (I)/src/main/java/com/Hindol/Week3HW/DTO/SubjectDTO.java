package com.Hindol.Week3HW.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class SubjectDTO {
    private Long id;
    private String title;
    private ProfessorDTO professor;
    private Set<StudentDTO> students;
}
