package com.Hindol.Week3HW.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class ProfessorDTO {
    private Long id;
    @Size(min = 3, max = 100, message = "The number of characters in the title of Professor should be in the range of [3, 100]")
    private String title;
    @JsonIgnore
    private Set<SubjectDTO> subjects;
    private Set<StudentDTO> students;
}
