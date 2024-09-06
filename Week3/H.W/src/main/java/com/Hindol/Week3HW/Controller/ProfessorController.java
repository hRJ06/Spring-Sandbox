package com.Hindol.Week3HW.Controller;

import com.Hindol.Week3HW.DTO.ProfessorDTO;
import com.Hindol.Week3HW.DTO.StudentDTO;
import com.Hindol.Week3HW.DTO.SubjectDTO;
import com.Hindol.Week3HW.Service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }
    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> getAllProfessor() {
        return ResponseEntity.ok(professorService.getAllProfessors());
    }
    @GetMapping("/{professorId}")
    public ResponseEntity<ProfessorDTO> getProfessorById(@PathVariable Long professorId) {
        return ResponseEntity.ok(professorService.getProfessorById(professorId));
    }
    @PutMapping("/{professorId}")
    public ResponseEntity<ProfessorDTO> updateProfessorById(@RequestBody @Valid ProfessorDTO professorDTO, @PathVariable Long professorId) {
        return ResponseEntity.ok(professorService.updateProfessorById(professorId, professorDTO));
    }
    @DeleteMapping("/{professorId}")
    public ResponseEntity<?> deleteProfessorById(@PathVariable Long professorId) {
        return new ResponseEntity<>(Map.of("success", professorService.deleteProfessorById(professorId)), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ProfessorDTO> createProfessor(@RequestBody @Valid ProfessorDTO professorDTO) {
        return new ResponseEntity<>(professorService.createProfessor(professorDTO), HttpStatus.CREATED);
    }
    @GetMapping("/assignedStudents/{professorId}")
    public ResponseEntity<List<StudentDTO>> getEnrolledStudents(@PathVariable Long professorId) {
        return ResponseEntity.ok(professorService.getAssignedStudents(professorId));
    }
    @GetMapping("/assignedSubjects/{professorId}")
    public ResponseEntity<List<SubjectDTO>> getAssignedSubjects(@PathVariable Long professorId) {
        return ResponseEntity.ok(professorService.getAssignedSubjects(professorId));
    }
    @PutMapping("/{professorId}/assignStudent/{studentId}")
    public ResponseEntity<ProfessorDTO> assignStudentToProfessor(@PathVariable Long professorId, @PathVariable Long studentId) {
        return ResponseEntity.ok(professorService.assignStudentToProfessor(studentId, professorId));
    }

}
