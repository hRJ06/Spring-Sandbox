package com.Hindol.H2Database.Controller;

import com.Hindol.H2Database.DTO.EmployeeDTO;
import com.Hindol.H2Database.Service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    private EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return this.employeeService.createEmployee(employeeDTO);
    }

    @GetMapping
    private List<EmployeeDTO> getAllEmployees() {
        return this.employeeService.getAllEmployees();
    }
}
