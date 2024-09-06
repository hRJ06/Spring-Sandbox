package com.Hindol.Week3HW.Service.Implementation;

import com.Hindol.Week3HW.DTO.ProfessorDTO;
import com.Hindol.Week3HW.DTO.StudentDTO;
import com.Hindol.Week3HW.DTO.SubjectDTO;
import com.Hindol.Week3HW.Entity.ProfessorEntity;
import com.Hindol.Week3HW.Entity.StudentEntity;
import com.Hindol.Week3HW.Exception.ResourceNotFoundException;
import com.Hindol.Week3HW.Repository.ProfessorRepository;
import com.Hindol.Week3HW.Repository.StudentRepository;
import com.Hindol.Week3HW.Service.ProfessorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorServiceImplementation implements ProfessorService {
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    private void checkIfProfessorExistsById(Long id) {
        boolean check = this.professorRepository.existsById(id);
        if(!check) throw new ResourceNotFoundException("No Professor found with ID : " + id);
    }
    public ProfessorServiceImplementation(ProfessorRepository professorRepository, StudentRepository studentRepository, ModelMapper modelMapper) {
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProfessorDTO> getAllProfessors() {
        List<ProfessorEntity> professors = this.professorRepository.findAll();
        return professors.stream().map(professor -> this.modelMapper.map(professor, ProfessorDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ProfessorDTO getProfessorById(Long professorId) {
        checkIfProfessorExistsById(professorId);
        return modelMapper.map(this.professorRepository.findById(professorId).orElse(null), ProfessorDTO.class);
    }

    @Override
    public ProfessorDTO updateProfessorById(Long professorId, ProfessorDTO professorDTO) {
        checkIfProfessorExistsById(professorId);
        ProfessorEntity professor = this.modelMapper.map(professorDTO, ProfessorEntity.class);
        professor.setId(professorId);
        return modelMapper.map(this.professorRepository.save(professor), ProfessorDTO.class);
    }

    @Override
    public Boolean deleteProfessorById(Long professorId) {
        checkIfProfessorExistsById(professorId);
        ProfessorEntity professor = this.professorRepository.findById(professorId).orElse(null);
        for(StudentEntity student : professor.getStudents()) {
            student.getProfessors().remove(professor);
            /* TODO: */
            studentRepository.save(student);

        }
        this.professorRepository.deleteById(professorId);
        return true;
    }

    @Override
    public ProfessorDTO createProfessor(ProfessorDTO professorDTO) {
        return modelMapper.map(professorRepository.save(modelMapper.map(professorDTO, ProfessorEntity.class)), ProfessorDTO.class);
    }

    @Override
    public List<StudentDTO> getAssignedStudents(Long professorId) {
        checkIfProfessorExistsById(professorId);
        ProfessorEntity professor = this.professorRepository.findById(professorId).orElse(null);
        return professor.getStudents().stream().map(studentEntity -> modelMapper.map(studentEntity, StudentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<SubjectDTO> getAssignedSubjects(Long professorId) {
        checkIfProfessorExistsById(professorId);
        ProfessorEntity professor = this.professorRepository.findById(professorId).orElse(null);
        return professor.getSubjects().stream().map(subjectEntity -> modelMapper.map(subjectEntity, SubjectDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ProfessorDTO assignStudentToProfessor(Long studentId, Long professorId) {
        checkIfProfessorExistsById(professorId);
        StudentEntity student = this.studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("No Student found with ID : " + studentId));
        ProfessorEntity professor = this.professorRepository.findById(professorId).orElse(null);
        professor.getStudents().add(student);
        return modelMapper.map(this.professorRepository.save(professor), ProfessorDTO.class);
    }
}
