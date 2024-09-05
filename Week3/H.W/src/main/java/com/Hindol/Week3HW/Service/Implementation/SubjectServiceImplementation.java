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
import com.Hindol.Week3HW.Service.SubjectService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SubjectServiceImplementation implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public SubjectServiceImplementation(SubjectRepository subjectRepository, ProfessorRepository professorRepository, StudentRepository studentRepository, ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    private void checkIfSubjectExitsById(Long subjectId) {
        boolean exits = subjectRepository.existsById(subjectId);
        if(!exits) throw new RuntimeException("No Subject exits by ID : " + subjectId);
    }


    @Override
    public List<SubjectDTO> getAllSubjects() {
        List<SubjectEntity> subjectEntities = subjectRepository.findAll();
        return subjectEntities.stream().map(subjectEntity -> modelMapper.map(subjectEntity, SubjectDTO.class)).collect(Collectors.toList());
    }

    @Override
    public SubjectDTO getSubjectById(Long subjectId) {
        checkIfSubjectExitsById(subjectId);
        SubjectEntity subjectEntity = subjectRepository.findById(subjectId).orElse(null);
        return this.modelMapper.map(subjectEntity, SubjectDTO.class);
    }

    @Override
    public SubjectDTO updateSubjectById(Long subjectId, SubjectDTO subjectDTO) {
        checkIfSubjectExitsById(subjectId);
        subjectDTO.setId(subjectId);
        return modelMapper.map(subjectRepository.save(modelMapper.map(subjectDTO, SubjectEntity.class)), SubjectDTO.class);
    }

    @Override
    public Boolean deleteSubjectById(Long subjectId) {
        checkIfSubjectExitsById(subjectId);
        subjectRepository.deleteById(subjectId);
        return true;
    }

    @Override
    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        SubjectEntity subjectEntity = this.modelMapper.map(subjectDTO, SubjectEntity.class);
        SubjectEntity createdSubject = this.subjectRepository.save(subjectEntity);
        return this.modelMapper.map(createdSubject, SubjectDTO.class);
    }

    @Override
    public ProfessorDTO getAssignedProfessor(Long subjectId) {
        checkIfSubjectExitsById(subjectId);
        return this.modelMapper.map(subjectRepository.findById(subjectId).orElse(null).getProfessor(), ProfessorDTO.class);
    }

    @Override
    public List<StudentDTO> getEnrolledStudents(Long subjectId) {
        checkIfSubjectExitsById(subjectId);
        SubjectEntity subject = this.subjectRepository.findById(subjectId).orElse(null);
        Set<StudentEntity> students = subject.getStudents();
        return students.stream().map(student -> this.modelMapper.map(student, StudentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public SubjectDTO assignProfessor(Long subjectId, Long professorId) {
        checkIfSubjectExitsById(subjectId);
        ProfessorEntity professor = this.professorRepository.findById(professorId).orElseThrow(() -> new ResourceNotFoundException("No Professor found with ID : " + professorId));
        SubjectEntity subjectEntity = this.subjectRepository.findById(subjectId).orElse(null);
        subjectEntity.setProfessor(professor);
        professor.getSubjects().add(subjectEntity);
        return modelMapper.map(subjectRepository.save(subjectEntity), SubjectDTO.class);
    }

    @Override
    public SubjectDTO enrollStudent(Long subjectId, Long studentId) {
        checkIfSubjectExitsById(subjectId);
        StudentEntity student = this.studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Unable to find Student with ID : " + studentId));
        SubjectEntity subject = this.subjectRepository.findById(subjectId).orElse(null);
        subject.getStudents().add(student);
        student.getSubjects().add(subject);
        return modelMapper.map(subjectRepository.save(subject), SubjectDTO.class);
    }
}
