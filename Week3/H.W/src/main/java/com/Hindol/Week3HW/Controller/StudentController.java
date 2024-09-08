package com.Hindol.Week3HW.Controller;

import com.Hindol.Week3HW.DTO.ProfessorDTO;
import com.Hindol.Week3HW.DTO.StudentDTO;
import com.Hindol.Week3HW.DTO.SubjectDTO;
import com.Hindol.Week3HW.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getStudentById(studentId));
    }
    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDTO> updatedStudentById(@PathVariable Long studentId, @RequestBody Map<String, Object> fieldsToBeChanged) {
        return ResponseEntity.ok(studentService.updateStudentById(studentId, fieldsToBeChanged));
    }
    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudentById(@PathVariable Long studentId) {
        return new ResponseEntity<>(Map.of("success", studentService.deleteStudentById(studentId)), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody @Valid StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.createStudent(studentDTO));
    }
    @GetMapping("/assignedProfessor/{studentId}")
    public ResponseEntity<List<ProfessorDTO>> getAssignedProfessors(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getAssignedProfessors(studentId));
    }
    @GetMapping("/enrolledSubjects/{studentId}")
    public ResponseEntity<List<SubjectDTO>> getEnrolledSubjects(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getEnrolledSubjects(studentId));
    }
}
