package com.Hindol.Test.Service.Implementation;

import com.Hindol.Test.DTO.EmployeeDTO;
import com.Hindol.Test.Entity.Employee;
import com.Hindol.Test.Repository.EmployeeRepository;
import com.Hindol.Test.TestContainerConfiguration;
import org.assertj.core.api.AssertJProxySetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/* @Import(TestContainerConfiguration.class)
 @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) */
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplementationTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImplementation employeeServiceImplementation;

    private Employee mockEmployee;
    private EmployeeDTO mockEmployeeDTO;

    @BeforeEach
    void setUp() {
        mockEmployee = Employee.builder().id(1L).name("Hindol Roy").email("hindol.roy@gmail.com").salary(200L).build();
        mockEmployeeDTO = modelMapper.map(mockEmployee, EmployeeDTO.class);
    }
    @Test
    void testGetEmployeeId_WhenEmployeeIdIsPresent_thenReturnEmployeeDTO() {
        /* employeeServiceImplementation.getEmployeeById(1L); */

        /* Assign */
        Long id = mockEmployee.getId();
        /* Employee mockEmployee = Employee.builder().id(id).name("Hindol Roy").email("hindol.roy@gmail.com").salary(200L).build(); - Stubbing */
        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee));
        /* Act */
        EmployeeDTO employeeDTO = employeeServiceImplementation.getEmployeeById(id);
        /* Assert */
        assertThat(employeeDTO).isNotNull();
        assertThat(employeeDTO.getId()).isEqualTo(id);
        assertThat(employeeDTO.getEmail()).isEqualTo(mockEmployee.getEmail());
        verify(employeeRepository, times(1)).findById(id);
    }

    @Test
    void testCreateNewEmployee_WhenValidEmployee_ThenCreateNewEmployee() {
        /* Assign */
        when(employeeRepository.findByEmail("hindol.roy@gmail.com")).thenReturn(List.of());
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);
        /* Act */
        EmployeeDTO employeeDTO = employeeServiceImplementation.createNewEmployee(mockEmployeeDTO);
        /* Assert */
        assertThat(employeeDTO).isNotNull();
        assertThat(employeeDTO.getEmail()).isEqualTo(mockEmployeeDTO.getEmail());
        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);

        verify(employeeRepository, times(1)).save(employeeArgumentCaptor.capture());
        Employee capturedEmployee = employeeArgumentCaptor.getValue();

        assertThat(capturedEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());
    }

}