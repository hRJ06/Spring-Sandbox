package com.Hindol.Test.Controller;

import com.Hindol.Test.DTO.EmployeeDTO;
import com.Hindol.Test.Entity.Employee;
import com.Hindol.Test.Repository.EmployeeRepository;
import com.Hindol.Test.TestContainerConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class EmployeeControllerTestIT extends AbstractIntegrationTest{

    @Autowired
    private EmployeeRepository employeeRepository;

    /*
        private Employee testEmployee;
        private EmployeeDTO testEmployeeDTO;
    */

    @BeforeEach
    void setUp() {
        /*
            testEmployee = Employee.builder()
                    .id(1L)
                    .email("hindol.roy@gmail.com")
                    .name("Hindol Roy")
                    .salary(200L)
                    .build();
            testEmployeeDTO = EmployeeDTO.builder()
                    .id(1L)
                    .email("hindol.roy@gmail.com")
                    .name("Hindol Roy")
                    .salary(200L).build();
         */
        employeeRepository.deleteAll();
    }

    @Test
    void testGetEmployeeById_Success() {
        Employee savedEmployee = employeeRepository.save(testEmployee);
        webTestClient.get()
                .uri("/employee/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                /*.expectBody(EmployeeDTO.class)
                .isEqualTo(testEmployeeDTO); */
                .expectBody()
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.email").isEqualTo(savedEmployee.getEmail());
                /* .value(employeeDTO -> {
                    assertThat(employeeDTO.getEmail()).isEqualTo(savedEmployee.getEmail());
                    assertThat(employeeDTO.getId()).isEqualTo(savedEmployee.getId());
                }); */
    }

    @Test
    void testGetEmployeeById_Failure() {
        webTestClient.get()
                .uri("/employee/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateNewEmployee_whenEmployeeAlreadyExists_thenThrowException() {
        Employee savedEmployee = employeeRepository.save(testEmployee);
        webTestClient.post()
                .uri("/employee")
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testCreateNewEmployee_whenEmployeeDoesNotExists_thenCreateEmployee() {
        webTestClient.post()
                .uri("/employee")
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(testEmployeeDTO.getEmail())
                .jsonPath("$.name").isEqualTo(testEmployeeDTO.getName());
    }

    @Test
    void testUpdateEmployee_whenEmployeeDoesNotExists_thenThrowException() {
        webTestClient.put()
                .uri("/employee/2")
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateEmployee_whenAttemptingToUpdateTheEmail_thenThrowException() {
        Employee savedEmployee = employeeRepository.save(testEmployee);
        testEmployeeDTO.setName("John Doe");
        testEmployeeDTO.setEmail("random@gmail.com");
        webTestClient.put()
                .uri("/employee/{id}", savedEmployee.getId())
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().is5xxServerError();

    }

    @Test
    void testUpdateEmployee_whenEmployeeIsValid_thenUpdateEmployee() {
        Employee savedEmployee = employeeRepository.save(testEmployee);
        testEmployeeDTO.setName("John Doe");
        testEmployeeDTO.setSalary(250L);
        webTestClient.put()
                .uri("/employee/{id}", savedEmployee.getId())
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDTO.class)
                .isEqualTo(testEmployeeDTO);
    }

    @Test
    void testDeleteEmployee_whenEmployeeDoesNotExists_thenThrowException() {
        webTestClient.delete()
                .uri("/employee/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteEmployee_whenEmployeeExists_thenDeleteEmployee() {
        Employee savedEmployee = employeeRepository.save(testEmployee);
        webTestClient.delete()
                .uri("/employee/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Void.class);

        webTestClient.delete()
                .uri("/employee/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isNotFound();
    }
}