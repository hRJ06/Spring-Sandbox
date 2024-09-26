package com.Hindol.Test.Service;

import com.Hindol.Test.DTO.EmployeeDTO;

public interface EmployeeService {
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
    void deleteEmployee(Long id);
}
