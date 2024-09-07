package com.Hindol.Week3HW.Service.Implementation;

import com.Hindol.Week3HW.DTO.AdmissionRecordDTO;
import com.Hindol.Week3HW.Entity.AdmissionRecordEntity;
import com.Hindol.Week3HW.Entity.StudentEntity;
import com.Hindol.Week3HW.Exception.ResourceNotFoundException;
import com.Hindol.Week3HW.Repository.AdmissionRecordRepository;
import com.Hindol.Week3HW.Repository.StudentRepository;
import com.Hindol.Week3HW.Service.AdmissionRecordService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdmissionRecordServiceImplementation implements AdmissionRecordService {
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;
    private final AdmissionRecordRepository admissionRecordRepository;

    public AdmissionRecordServiceImplementation(ModelMapper modelMapper, StudentRepository studentRepository, AdmissionRecordRepository admissionRecordRepository) {
        this.modelMapper = modelMapper;
        this.studentRepository = studentRepository;
        this.admissionRecordRepository = admissionRecordRepository;
    }

    private void checkIfAdmissionRecordExists(Long admissionRecordId) {
        boolean check = this.admissionRecordRepository.existsById(admissionRecordId);
        if(!check) throw new ResourceNotFoundException("No Admission Record found with ID : " + admissionRecordId);
    }

    @Override
    public AdmissionRecordDTO enrollStudent(Long studentId, AdmissionRecordDTO admissionRecordDTO) {
        StudentEntity student = this.studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("No Student found with ID : " + studentId));
        Optional<AdmissionRecordEntity> admissionRecord = this.admissionRecordRepository.findByStudent(student);
        if(admissionRecord.isPresent()) {
            return null;
        }
        AdmissionRecordEntity admissionRecordEntity = modelMapper.map(admissionRecordDTO, AdmissionRecordEntity.class);
        admissionRecordEntity.setStudent(student);
        AdmissionRecordEntity createdAdmissionRecord = this.admissionRecordRepository.save(admissionRecordEntity);
        return modelMapper.map(createdAdmissionRecord, AdmissionRecordDTO.class);
    }

    @Override
    public List<AdmissionRecordDTO> getAllEnrolledStudents() {
        List<AdmissionRecordEntity> admissionRecords = this.admissionRecordRepository.findAll();
        return admissionRecords.stream().map(admissionRecordEntity -> this.modelMapper.map(admissionRecordEntity, AdmissionRecordDTO.class)).collect(Collectors.toList());
    }

    @Override
    public AdmissionRecordDTO getEnrolledStudentRecordById(Long enrollmentId) {
        checkIfAdmissionRecordExists(enrollmentId);
        return modelMapper.map(admissionRecordRepository.findById(enrollmentId).orElse(null), AdmissionRecordDTO.class);
    }

    @Override
    public Boolean deleteStudentEnrollmentDetails(Long studentId) {
        StudentEntity student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("No Student found with ID : " + studentId));
        student.setAdmissionRecord(null);
        studentRepository.save(student);
        return true;
    }
}
