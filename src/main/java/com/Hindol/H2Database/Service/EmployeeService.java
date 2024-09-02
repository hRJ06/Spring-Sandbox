package com.Hindol.H2Database.Service;

import com.Hindol.H2Database.DTO.EmployeeDTO;
import com.Hindol.H2Database.Entity.EmployeeEntity;
import com.Hindol.H2Database.Exception.ResourceNotFoundException;
import com.Hindol.H2Database.Repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    /* CONSTRUCTOR INJECTION */
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    void checkIfEmployeeExits(Integer employeeId) {
       boolean check = this.employeeRepository.existsById(employeeId);
       if(!check) throw new ResourceNotFoundException("Unable to find Employee with ID : " + employeeId);
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = this.modelMapper.map(employeeDTO, EmployeeEntity.class);
        EmployeeEntity savedEmployee = this.employeeRepository.save(employeeEntity);
        return this.modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    public EmployeeDTO getEmployeeById(Integer employeeId) {
        checkIfEmployeeExits(employeeId);
        return this.modelMapper.map(this.employeeRepository.findById(employeeId).get(), EmployeeDTO.class);
    }
    
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employees = this.employeeRepository.findAll();
        return employees.stream().map(employee -> this.modelMapper.map(employee, EmployeeDTO.class)).collect(Collectors.toList());
    }

    public EmployeeDTO updateEmployee(Integer employeeId, EmployeeDTO employeeDTO) {
        checkIfEmployeeExits(employeeId);
        EmployeeEntity savedEmployee = this.modelMapper.map(employeeDTO, EmployeeEntity.class);
        savedEmployee.setId(employeeId);
        return modelMapper.map(this.employeeRepository.save(savedEmployee), EmployeeDTO.class);
    }

    public Boolean deleteEmployee(Integer employeeId) {
        checkIfEmployeeExits(employeeId);
        this.employeeRepository.deleteById(employeeId);
        return true;
    }

    public EmployeeDTO partialUpdateEmployee(Integer employeeId, Map<String, Object> fieldsToBeUpdated) {
        checkIfEmployeeExits(employeeId);
        EmployeeEntity employee = this.employeeRepository.findById(employeeId).get();
        fieldsToBeUpdated.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employee, value);
        });
        return modelMapper.map(this.employeeRepository.save(employee), EmployeeDTO.class);
    }
}

