package com.Hindol.Week3HW.Service;

import com.Hindol.Week3HW.DTO.AdmissionRecordDTO;

import java.util.List;

public interface AdmissionRecordService {
    public AdmissionRecordDTO enrollStudent(Long studentId, AdmissionRecordDTO admissionRecordDTO);
    public List<AdmissionRecordDTO> getAllEnrolledStudents();
    public AdmissionRecordDTO getEnrolledStudentRecordById(Long enrollmentId);
    public Boolean deleteStudentEnrollmentDetails(Long enrollmentId);
}
