package com.Hindol.H2Database.Controller;

import com.Hindol.H2Database.DTO.EmployeeDTO;
import com.Hindol.H2Database.Service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(path = "/create")
    private EmployeeDTO createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return this.employeeService.createEmployee(employeeDTO);
    }

    @GetMapping
    private List<EmployeeDTO> getAllEmployees() {
        return this.employeeService.getAllEmployees();
    }

    @GetMapping(path = "/{employeeId}")
    private ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Integer employeeId) {
        Optional<EmployeeDTO> employeeDTO = this.employeeService.getEmployeeById(employeeId);
        return employeeDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /* TO UPDATE ENTIRE EMPLOYEE */
    @PutMapping(path = "/{employeeId}")
    private ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Integer employeeId, @RequestBody @Valid EmployeeDTO employeeDTO) {
        EmployeeDTO updatedEmployeeDTO = this.employeeService.updateEmployee(employeeId, employeeDTO);
        if(updatedEmployeeDTO != null) {
            return ResponseEntity.ok(updatedEmployeeDTO);
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping(path = "/{employeeId}")
    private ResponseEntity<Boolean> deleteEmployee(@PathVariable Integer employeeId) {
        Boolean deleted = this.employeeService.deleteEmployee(employeeId);
        return deleted ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{employeeId}")
    private ResponseEntity<EmployeeDTO> partialUpdateEmployee(@PathVariable(name = "employeeId") Integer id, @RequestBody Map<String, Object> fieldsToBeUpdated) {
        EmployeeDTO updatedEmployeeDTO = this.employeeService.partialUpdateEmployee(id, fieldsToBeUpdated);
        if(updatedEmployeeDTO != null) {
            return ResponseEntity.ok(updatedEmployeeDTO);
        }
        return ResponseEntity.notFound().build();
    }
}
