package com.Hindol.Week3HW.Controller;

import com.Hindol.Week3HW.DTO.AdmissionRecordDTO;
import com.Hindol.Week3HW.Service.AdmissionRecordService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admission-record")
public class AdmissionRecordController {
    private final AdmissionRecordService admissionRecordService;

    Logger log = LoggerFactory.getLogger(AdmissionRecordController.class);

    public AdmissionRecordController(AdmissionRecordService admissionRecordService) {
        this.admissionRecordService = admissionRecordService;
    }

    @PostMapping("/enrollStudent/{studentId}")
    public ResponseEntity<AdmissionRecordDTO> enrollStudent(@PathVariable Long studentId, @RequestBody @Valid AdmissionRecordDTO admissionRecordDTO) {
        log.info("STUDENT ID : {}, ADMISSION RECORD : {}", studentId, admissionRecordDTO);
        return ResponseEntity.ok(admissionRecordService.enrollStudent(studentId, admissionRecordDTO));
    }

    @GetMapping
    public ResponseEntity<List<AdmissionRecordDTO>> getAllEnrolledStudents() {
        return ResponseEntity.ok(admissionRecordService.getAllEnrolledStudents());
    }

    @GetMapping("/{enrollmentId}")
    public ResponseEntity<AdmissionRecordDTO> getEnrolledStudentRecordById(@PathVariable Long enrollmentId) {
        log.info("ENROLLMENT ID : {}", enrollmentId);
        return ResponseEntity.ok(admissionRecordService.getEnrolledStudentRecordById(enrollmentId));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudentEnrollmentDetails(@PathVariable Long studentId) {
        log.info("STUDENT ID : {}", studentId);
        return new ResponseEntity<>(Map.of("success", admissionRecordService.deleteStudentEnrollmentDetails(studentId)), HttpStatus.OK);
    }
}
