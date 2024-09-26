package com.Hindol.Test.Repository;

import com.Hindol.Test.Entity.Employee;
import com.Hindol.Test.TestContainerConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/* @SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) */
@DataJpaTest
@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder().id(1L).name("Hindol Roy").email("hindol.roy@gmail.com").salary(100L).build();
    }

    @Test
    void testFindByEmail_whenEmailIsValid_thenReturnEmployee() {
        /* Arrange, Given */
        employeeRepository.save(employee);

        /* Act, When */
        List<Employee> employeeList = employeeRepository.findByEmail(employee.getEmail());

        /* Assert, Then */
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isNotEmpty();
        assertThat(employeeList.get(0).getEmail()).isEqualTo(employee.getEmail());
    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList() {
        /* Arrange, Given */
        String email = "notPresent@gmail.com";

        /* Act, When */
        List<Employee> employeeList = employeeRepository.findByEmail(email);

        /* Assert, Then */
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isEmpty();
    }
}