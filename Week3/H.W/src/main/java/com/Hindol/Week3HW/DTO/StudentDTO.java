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
public class StudentDTO {
    private Long id;
    @Size(min = 3, max = 100, message = "The number of characters in the name of student should be in the range of [3, 100]")
    private String name;
    @JsonIgnore
    private AdmissionRecordDTO admissionRecord;
    @JsonIgnore
    private Set<SubjectDTO> subjects;
    @JsonIgnore
    private Set<ProfessorDTO> professors;
}
