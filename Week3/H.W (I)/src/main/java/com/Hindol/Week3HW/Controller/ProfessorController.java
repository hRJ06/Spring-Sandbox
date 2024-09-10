package com.Hindol.Week3HW.Controller;

import com.Hindol.Week3HW.DTO.ProfessorDTO;
import com.Hindol.Week3HW.DTO.StudentDTO;
import com.Hindol.Week3HW.DTO.SubjectDTO;
import com.Hindol.Week3HW.Service.ProfessorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
    private final ProfessorService professorService;

    Logger log = LoggerFactory.getLogger(ProfessorController.class);

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }
    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> getAllProfessor() {
        return ResponseEntity.ok(professorService.getAllProfessors());
    }
    @GetMapping("/{professorId}")
    public ResponseEntity<ProfessorDTO> getProfessorById(@PathVariable Long professorId) {
        log.info("PROFESSOR ID : {}", professorId);
        return ResponseEntity.ok(professorService.getProfessorById(professorId));
    }
    @PutMapping("/{professorId}")
    public ResponseEntity<ProfessorDTO> updateProfessorById(@RequestBody Map<String, Object> fieldsToBeChanged, @PathVariable Long professorId) {
        log.info("PROFESSOR ID : {}, FIELD TO BE UPDATED : {}", professorId, fieldsToBeChanged.toString());
        return ResponseEntity.ok(professorService.updateProfessorById(professorId, fieldsToBeChanged));
    }
    @DeleteMapping("/{professorId}")
    public ResponseEntity<?> deleteProfessorById(@PathVariable Long professorId) {
        log.info("PROFESSOR ID : {}", professorId);
        return new ResponseEntity<>(Map.of("success", professorService.deleteProfessorById(professorId)), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ProfessorDTO> createProfessor(@RequestBody @Valid ProfessorDTO professorDTO) {
        log.debug("PROFESSOR : {}", professorDTO);
        return new ResponseEntity<>(professorService.createProfessor(professorDTO), HttpStatus.CREATED);
    }
    @GetMapping("/assignedStudents/{professorId}")
    public ResponseEntity<List<StudentDTO>> getEnrolledStudents(@PathVariable Long professorId) {
        log.info("PROFESSOR ID : {}", professorId);
        return ResponseEntity.ok(professorService.getAssignedStudents(professorId));
    }
    @GetMapping("/assignedSubjects/{professorId}")
    public ResponseEntity<List<SubjectDTO>> getAssignedSubjects(@PathVariable Long professorId) {
        log.info("PROFESSOR ID : {}", professorId);
        return ResponseEntity.ok(professorService.getAssignedSubjects(professorId));
    }
    @PutMapping("/{professorId}/assignStudent/{studentId}")
    public ResponseEntity<ProfessorDTO> assignStudentToProfessor(@PathVariable Long professorId, @PathVariable Long studentId) {
        log.info("PROFESSOR ID : {}, STUDENT ID : {}", professorId, studentId);
        return ResponseEntity.ok(professorService.assignStudentToProfessor(studentId, professorId));
    }

}
