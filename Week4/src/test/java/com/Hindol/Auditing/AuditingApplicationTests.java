package com.Hindol.Auditing;

import com.Hindol.Auditing.Client.DepartmentClient;
import com.Hindol.Auditing.DTO.DepartmentDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuditingApplicationTests {

	@Autowired
	private DepartmentClient departmentClient;

	@Test
	@Order(3)
	void getAllDepartmentsTest() {
		List<DepartmentDTO> departmentDTOList = departmentClient.getAllDepartments();
		System.out.println(departmentDTOList);
	}

	@Test
	@Order(2)
	void getDepartmentByIdTest() {
		DepartmentDTO departmentDTO = departmentClient.getDepartmentById(100L);
		System.out.println(departmentDTO);
	}

	@Test
	@Order(1)
	void createDepartmentTest() {
		DepartmentDTO departmentDTO = new DepartmentDTO(null, "CCB", true, LocalDateTime.of(2024, 8, 8, 0, 0), 13L, "cI_B123456");
		DepartmentDTO createdDepartment = departmentClient.createNewDepartment(departmentDTO);
		System.out.println(createdDepartment);
	}
}
