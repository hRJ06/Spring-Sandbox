package com.Hindol.Week2HomeWork.Service;

import com.Hindol.Week2HomeWork.DTO.DepartmentDTO;
import com.Hindol.Week2HomeWork.Entity.DepartmentEntity;
import com.Hindol.Week2HomeWork.Exception.ResourceNotFoundException;
import com.Hindol.Week2HomeWork.Repository.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    private void checkIfDepartmentExist(Long departmentId) {
        boolean check = this.departmentRepository.existsById(departmentId);
        if(!check) throw new ResourceNotFoundException("No Department exists with ID " + departmentId);
    }
    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentEntity> departmentEntities = this.departmentRepository.findAll();
        return departmentEntities.stream().map(departmentEntity -> this.modelMapper.map(departmentEntity, DepartmentDTO.class)).collect(Collectors.toList());
    }

    public DepartmentDTO getDepartmentById(Long departmentId) {
        checkIfDepartmentExist(departmentId);
        DepartmentEntity departmentEntity = this.departmentRepository.findById(departmentId).orElse(null);
        return this.modelMapper.map(departmentEntity, DepartmentDTO.class);
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        DepartmentEntity departmentEntity = this.modelMapper.map(departmentDTO, DepartmentEntity.class);
        DepartmentEntity createdDepartmentEntity = this.departmentRepository.save(departmentEntity);
        return this.modelMapper.map(createdDepartmentEntity, DepartmentDTO.class);
    }

    public DepartmentDTO updateDepartmentById(DepartmentDTO departmentDTO, Long departmentId) {
        checkIfDepartmentExist(departmentId);
        DepartmentEntity departmentEntity = this.modelMapper.map(departmentDTO, DepartmentEntity.class);
        departmentEntity.setId(departmentId);
        DepartmentEntity updatedEntity = this.departmentRepository.save(departmentEntity);
        return this.modelMapper.map(updatedEntity, DepartmentDTO.class);
    }

    public DepartmentDTO updatePartialDepartmentById(Map<String, Object> fieldsToBeChanged, Long departmentId) {
        checkIfDepartmentExist(departmentId);
        DepartmentEntity departmentEntity = this.departmentRepository.findById(departmentId).orElse(null);
        fieldsToBeChanged.forEach((field, value) -> {
            Field fieldToBeChanged = ReflectionUtils.findRequiredField(DepartmentEntity.class, field);
            fieldToBeChanged.setAccessible(true);
            ReflectionUtils.setField(fieldToBeChanged, departmentEntity, value);
        });
        DepartmentEntity updatedDepartmentEntity = this.departmentRepository.save(departmentEntity);
        return this.modelMapper.map(updatedDepartmentEntity, DepartmentDTO.class);
    }

    public Boolean deleteDepartmentById(Long departmentId) {
        checkIfDepartmentExist(departmentId);
        this.departmentRepository.deleteById(departmentId);
        return true;
    }

}
