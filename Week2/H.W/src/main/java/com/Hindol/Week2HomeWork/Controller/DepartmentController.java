package com.Hindol.Week2HomeWork.Controller;

import com.Hindol.Week2HomeWork.DTO.DepartmentDTO;
import com.Hindol.Week2HomeWork.Service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment() {
        List<DepartmentDTO> departmentDTOS = this.departmentService.getAllDepartments();
        return ResponseEntity.ok(departmentDTOS);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long departmentId) {
        DepartmentDTO departmentDTO = this.departmentService.getDepartmentById(departmentId);
        return new ResponseEntity<>(departmentDTO, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody @Valid DepartmentDTO departmentDTO) {
        DepartmentDTO createdDepartmentDTO = this.departmentService.createDepartment(departmentDTO);
        return new ResponseEntity<>(createdDepartmentDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@RequestBody @Valid DepartmentDTO departmentDTO, @PathVariable Long departmentId) {
        DepartmentDTO updatedDepartmentDTO = this.departmentService.updateDepartmentById(departmentDTO, departmentId);
        return ResponseEntity.ok(updatedDepartmentDTO);
    }

    @PatchMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> partialUpdateDepartment(@RequestBody Map<String, Object> fieldsToBeChanged, @PathVariable Long departmentId) {
        DepartmentDTO updateDepartmentDTO = this.departmentService.updatePartialDepartmentById(fieldsToBeChanged, departmentId);
        return ResponseEntity.ok(updateDepartmentDTO);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable Long departmentId) {
        boolean isDeleted = this.departmentService.deleteDepartmentById(departmentId);
        return ResponseEntity.ok(isDeleted);
    }
}
