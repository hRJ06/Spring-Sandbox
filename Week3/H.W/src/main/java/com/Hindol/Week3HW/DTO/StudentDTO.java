package com.Hindol.Week3HW.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Set;

@Data
public class StudentDTO {
    private Long id;
    private String name;
    @JsonIgnore
    private AdmissionRecordDTO admissionRecord;
    private Set<SubjectDTO> subjects;
    @JsonIgnore
    private Set<ProfessorDTO> professors;
}
