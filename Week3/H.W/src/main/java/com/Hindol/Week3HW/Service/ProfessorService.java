package com.Hindol.Week3HW.Service;

import com.Hindol.Week3HW.DTO.ProfessorDTO;
import com.Hindol.Week3HW.DTO.StudentDTO;
import com.Hindol.Week3HW.DTO.SubjectDTO;

import java.util.List;

public interface ProfessorService {
    public List<ProfessorDTO> getAllProfessors();
    public ProfessorDTO getProfessorById(Long professorId);
    public ProfessorDTO updateProfessorById(Long professorId, ProfessorDTO professorDTO);
    public void deleteProfessorById(Long professorId);
    public ProfessorDTO createProfessor(ProfessorDTO professorDTO);
    public List<StudentDTO> getAssignedStudents(Long professorId);
    public List<SubjectDTO> getAssignedSubjects(Long professorId);
    public ProfessorDTO assignStudentToProfessor(Long studentId, Long professorId);
}
