package com.Hindol.Week3HW.Controller;

import com.Hindol.Week3HW.DTO.ProfessorDTO;
import com.Hindol.Week3HW.DTO.StudentDTO;
import com.Hindol.Week3HW.DTO.SubjectDTO;
import com.Hindol.Week3HW.Service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }
    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long subjectId) {
        return ResponseEntity.ok(subjectService.getSubjectById(subjectId));
    }
    @PutMapping("/{subjectId}")
    public ResponseEntity<SubjectDTO> updateSubjectById(@PathVariable Long subjectId, @RequestBody @Valid SubjectDTO subjectDTO) {
        return ResponseEntity.ok(subjectService.updateSubjectById(subjectId, subjectDTO));
    }
    @DeleteMapping("/{subjectId}")
    public ResponseEntity<?> deleteSubjectById(@PathVariable Long subjectId) {
        return new ResponseEntity<>(Map.of("success", subjectService.deleteSubjectById(subjectId)), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<SubjectDTO> createSubject(@RequestBody @Valid SubjectDTO subjectDTO) {
        return ResponseEntity.ok(subjectService.createSubject(subjectDTO));
    }
    @GetMapping("/assignedProfessor/{subjectId}")
    public ResponseEntity<ProfessorDTO> getAssignedProfessor(@PathVariable Long subjectId) {
        return ResponseEntity.ok(subjectService.getAssignedProfessor(subjectId));
    }
    @GetMapping("/enrolledStudents/{subjectId}")
    public ResponseEntity<List<StudentDTO>> getEnrolledStudents(@PathVariable Long subjectId) {
        return ResponseEntity.ok(subjectService.getEnrolledStudents(subjectId));
    }

    @PutMapping("/{subjectId}/assignProfessor/{professorId}")
    public ResponseEntity<SubjectDTO> assignProfessor(@PathVariable Long subjectId,@PathVariable Long professorId) {
        System.out.println("HII");
        return ResponseEntity.ok(subjectService.assignProfessor(subjectId, professorId));
    }
    @PutMapping("/{subjectId}/enrollStudent/{studentId}")
    public ResponseEntity<SubjectDTO> enrollStudent(@PathVariable Long subjectId,@PathVariable Long studentId) {
        return ResponseEntity.ok(subjectService.enrollStudent(subjectId, studentId));
    }

}
