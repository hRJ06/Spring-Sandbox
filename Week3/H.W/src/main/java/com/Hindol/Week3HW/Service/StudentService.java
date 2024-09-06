package com.Hindol.Week3HW.Service;

import com.Hindol.Week3HW.DTO.ProfessorDTO;
import com.Hindol.Week3HW.DTO.StudentDTO;
import com.Hindol.Week3HW.DTO.SubjectDTO;

import java.util.List;

public interface StudentService {
    public List<StudentDTO> getAllStudents();
    public StudentDTO getStudentById(Long studentId);
    public StudentDTO updateStudentById(Long studentId, StudentDTO studentDTO);
    public Boolean deleteStudentById(Long studentId);
    public StudentDTO createStudent(StudentDTO studentDTO);
    public List<ProfessorDTO> getAssignedProfessors(Long studentId);
    public List<SubjectDTO> getEnrolledSubjects(Long studentId);
}
