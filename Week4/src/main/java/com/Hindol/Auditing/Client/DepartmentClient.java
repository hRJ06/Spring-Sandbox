package com.Hindol.Auditing.Client;

import com.Hindol.Auditing.DTO.DepartmentDTO;

import java.util.List;

public interface DepartmentClient {
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO getDepartmentById(Long departmentId);
    DepartmentDTO createNewDepartment(DepartmentDTO departmentDTO);
}
