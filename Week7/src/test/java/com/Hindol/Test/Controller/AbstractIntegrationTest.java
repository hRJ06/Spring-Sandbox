package com.Hindol.Test.Controller;

import com.Hindol.Test.DTO.EmployeeDTO;
import com.Hindol.Test.Entity.Employee;
import com.Hindol.Test.TestContainerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
public class AbstractIntegrationTest {
    @Autowired
    public WebTestClient webTestClient;
    Employee testEmployee = Employee.builder()
            .id(1L)
                .email("hindol.roy@gmail.com")
                .name("Hindol Roy")
                .salary(200L)
                .build();
    EmployeeDTO testEmployeeDTO = EmployeeDTO.builder()
            .id(1L)
                .email("hindol.roy@gmail.com")
                .name("Hindol Roy")
                .salary(200L).build();
}
