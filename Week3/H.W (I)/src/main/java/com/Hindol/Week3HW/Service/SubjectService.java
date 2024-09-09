package com.Hindol.Week3HW.Service;

import com.Hindol.Week3HW.DTO.ProfessorDTO;
import com.Hindol.Week3HW.DTO.StudentDTO;
import com.Hindol.Week3HW.DTO.SubjectDTO;


import java.util.List;
import java.util.Map;

public interface SubjectService {
    public List<SubjectDTO> getAllSubjects();
    public SubjectDTO getSubjectById(Long subjectId);
    public SubjectDTO updateSubjectById(Long subjectId, Map<String, Object> fieldsToBeChanged);
    public Boolean deleteSubjectById(Long subjectId);
    public SubjectDTO createSubject(SubjectDTO subjectDTO);
    public ProfessorDTO getAssignedProfessor(Long SubjectId);
    public List<StudentDTO> getEnrolledStudents(Long subjectId);
    public SubjectDTO assignProfessor(Long subjectId, Long professorId);
    public SubjectDTO enrollStudent(Long subjectId, Long studentId);
}
