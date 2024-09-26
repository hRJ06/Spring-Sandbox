package com.Hindol.Test.Service.Implementation;

import com.Hindol.Test.DTO.EmployeeDTO;
import com.Hindol.Test.Entity.Employee;
import com.Hindol.Test.Exception.ResourceNotFoundException;
import com.Hindol.Test.Repository.EmployeeRepository;
import com.Hindol.Test.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImplementation implements EmployeeService {
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    Logger log = LoggerFactory.getLogger(EmployeeServiceImplementation.class);
    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        log.info("Fetching Employee with ID : {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Employee found with ID : " + id));
        log.info("Successfully fetched Employee with ID : {}", id);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        log.info("Creating new Employee with email : {}", employeeDTO.getEmail());
        List<Employee> existingEmployees = employeeRepository.findByEmail(employeeDTO.getEmail());
        if(!existingEmployees.isEmpty()) {
            log.error("Employee already exists with email : {}", employeeDTO.getEmail());
            throw new RuntimeException("Employee already exists with email " + employeeDTO.getEmail());
        }
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Successfully created new employee with ID : {}", savedEmployee.getId());
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        log.info("Updating Employee with ID : {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> {
            log.error("Employee not found with ID : {}", id);
            throw new ResourceNotFoundException("No Employee found with ID " + id);
        });
        if(!employeeDTO.getEmail().equals(employee.getEmail())) {
            log.error("Attempted to update email for Employee with ID : {}", id);
            throw new RuntimeException("The email of employee cannot be updated.");
        }
        modelMapper.map(employeeDTO, employee);
        employee.setId(id);
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Successfully updated Employee with ID : {}", id);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting Employee with ID : {}", id);
        boolean exists = employeeRepository.existsById(id);
        if(!exists) {
            log.error("Employee not found with ID : {}", id);
            throw new ResourceNotFoundException("No Employee found with ID " + id);
        }
        employeeRepository.deleteById(id);
        log.info("Successfully deleted Employee with ID : {}", id);
    }

}
