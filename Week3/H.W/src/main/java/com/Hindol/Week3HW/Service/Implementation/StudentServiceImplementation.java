package com.Hindol.Week3HW.Service.Implementation;

import com.Hindol.Week3HW.DTO.ProfessorDTO;
import com.Hindol.Week3HW.DTO.StudentDTO;
import com.Hindol.Week3HW.DTO.SubjectDTO;
import com.Hindol.Week3HW.Entity.ProfessorEntity;
import com.Hindol.Week3HW.Entity.StudentEntity;
import com.Hindol.Week3HW.Entity.SubjectEntity;
import com.Hindol.Week3HW.Exception.ResourceNotFoundException;
import com.Hindol.Week3HW.Repository.ProfessorRepository;
import com.Hindol.Week3HW.Repository.StudentRepository;
import com.Hindol.Week3HW.Repository.SubjectRepository;
import com.Hindol.Week3HW.Service.StudentService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentServiceImplementation implements StudentService {
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;

    public StudentServiceImplementation(StudentRepository studentRepository, ProfessorRepository professorRepository, SubjectRepository subjectRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    private void checkIfStudentExistsById(Long studentId) {
        boolean check = this.studentRepository.existsById(studentId);
        if(!check) throw new ResourceNotFoundException("No Student exist with ID : " + studentId);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<StudentEntity> students = this.studentRepository.findAll();
        return students.stream().map(student -> this.modelMapper.map(student, StudentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public StudentDTO getStudentById(Long studentId) {
        checkIfStudentExistsById(studentId);
        StudentEntity student = this.studentRepository.findById(studentId).orElse(null);
        return this.modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public StudentDTO updateStudentById(Long studentId, StudentDTO studentDTO) {
        checkIfStudentExistsById(studentId);
        StudentEntity student = this.modelMapper.map(studentDTO, StudentEntity.class);
        student.setId(studentId);
        return this.modelMapper.map(this.studentRepository.save(student), StudentDTO.class);
    }

    @Override
    public Boolean deleteStudentById(Long studentId) {
        checkIfStudentExistsById(studentId);
        StudentEntity student = this.studentRepository.findById(studentId).orElse(null);
        for(ProfessorEntity professor : student.getProfessors()) {
            professor.getStudents().remove(student);
            professorRepository.save(professor);
        }
        for(SubjectEntity subject : student.getSubjects()) {
            subject.getStudents().remove(student);
            subjectRepository.save(subject);

        }
        this.studentRepository.deleteById(studentId);
        return true;
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        StudentEntity createdStudent = this.modelMapper.map(studentDTO, StudentEntity.class);
        return this.modelMapper.map(createdStudent, StudentDTO.class);
    }

    @Override
    public List<ProfessorDTO> getAssignedProfessor(Long studentId) {
        checkIfStudentExistsById(studentId);
        StudentEntity student = this.studentRepository.findById(studentId).orElse(null);
        Set<ProfessorEntity> assignedProfessors = student.getProfessors();
        return assignedProfessors.stream().map(professorEntity -> this.modelMapper.map(professorEntity, ProfessorDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<SubjectDTO> getEnrolledSubjects(Long studentId) {
        checkIfStudentExistsById(studentId);
        StudentEntity student = this.studentRepository.findById(studentId).orElse(null);
        Set<SubjectEntity> enrolledSubjects = student.getSubjects();
        return enrolledSubjects.stream().map(subjectEntity -> this.modelMapper.map(subjectEntity, SubjectDTO.class)).collect(Collectors.toList());
    }
}
