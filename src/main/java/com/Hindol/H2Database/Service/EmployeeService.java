package com.Hindol.H2Database.Service;

import com.Hindol.H2Database.DTO.EmployeeDTO;
import com.Hindol.H2Database.Entity.EmployeeEntity;
import com.Hindol.H2Database.Repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = this.modelMapper.map(employeeDTO, EmployeeEntity.class);
        EmployeeEntity savedEmployee = this.employeeRepository.save(employeeEntity);
        return this.modelMapper.map(savedEmployee, EmployeeDTO.class);
    }
    
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employees = this.employeeRepository.findAll();
        return employees.stream().map(employee -> this.modelMapper.map(employee, EmployeeDTO.class)).collect(Collectors.toList());
    }

}

