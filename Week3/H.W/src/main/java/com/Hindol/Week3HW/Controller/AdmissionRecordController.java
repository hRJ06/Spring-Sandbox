package com.Hindol.Week3HW.Controller;

import com.Hindol.Week3HW.DTO.AdmissionRecordDTO;
import com.Hindol.Week3HW.Service.AdmissionRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admission-record")
public class AdmissionRecordController {
    private final AdmissionRecordService admissionRecordService;

    public AdmissionRecordController(AdmissionRecordService admissionRecordService) {
        this.admissionRecordService = admissionRecordService;
    }

    @PostMapping("/enrollStudent/{studentId}")
    public ResponseEntity<AdmissionRecordDTO> enrollStudent(@PathVariable Long studentId, @RequestBody @Valid AdmissionRecordDTO admissionRecordDTO) {
        return ResponseEntity.ok(admissionRecordService.enrollStudent(studentId, admissionRecordDTO));
    }

    @GetMapping
    public ResponseEntity<List<AdmissionRecordDTO>> getAllEnrolledStudents() {
        return ResponseEntity.ok(admissionRecordService.getAllEnrolledStudents());
    }

    @GetMapping("/{enrollmentId}")
    public ResponseEntity<AdmissionRecordDTO> etEnrolledStudentRecordById(@PathVariable Long enrollmentId) {
        return ResponseEntity.ok(admissionRecordService.getEnrolledStudentRecordById(enrollmentId));
    }

    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<?> deleteStudentEnrollmentDetails(@PathVariable Long enrollmentId) {
        return new ResponseEntity<>(Map.of("success", admissionRecordService.deleteStudentEnrollmentDetails(enrollmentId)), HttpStatus.OK);
    }
}
