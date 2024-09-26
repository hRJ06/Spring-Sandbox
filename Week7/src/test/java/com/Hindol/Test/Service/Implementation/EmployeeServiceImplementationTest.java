package com.Hindol.Test.Service.Implementation;

import com.Hindol.Test.DTO.EmployeeDTO;
import com.Hindol.Test.Entity.Employee;
import com.Hindol.Test.Exception.ResourceNotFoundException;
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

import static org.assertj.core.api.Assertions.*;
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
    void testGetEmployeeId_WhenEmployeeIdIsPresent_ThenReturnEmployeeDTO() {
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
    void testGetEmployeeById_WhenEmployeeIdIsNotPresent_ThenThrowException() {
        /* Arrange */
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        /* Act */
        assertThatThrownBy(() -> employeeServiceImplementation.getEmployeeById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No Employee found with ID : 1");
        verify(employeeRepository).findById(1L);
        /* Assert */
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

    @Test
    void testCreateNewEmployee_whenAttemptingToCreateEmployeeWithExistingEmail_thenThrowException() {
        /* Arrange */
        when(employeeRepository.findByEmail(mockEmployeeDTO.getEmail())).thenReturn(List.of(mockEmployee));

        /* Act & Assert */
        assertThatThrownBy(() -> employeeServiceImplementation.createNewEmployee(mockEmployeeDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee already exists with email " + mockEmployeeDTO.getEmail());
        verify(employeeRepository).findByEmail(mockEmployeeDTO.getEmail());
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void testUpdateEmployee_whenEmployeeDoesNotExists_thenThrowException() {
        /* Arrange */
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        /* Act & Assert */
        assertThatThrownBy(() -> employeeServiceImplementation.updateEmployee(1L, mockEmployeeDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No Employee found with ID 1");
        verify(employeeRepository).findById(1L);
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void testUpdateEmployee_whenAttemptingToUpdateEmail_thenThrowException() {
        /* Arrange */
        when(employeeRepository.findById(mockEmployeeDTO.getId())).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDTO.setName("John");
        mockEmployeeDTO.setEmail("random@gmail.com");
        /* Act & Assert */
        assertThatThrownBy(() -> employeeServiceImplementation.updateEmployee(mockEmployeeDTO.getId(), mockEmployeeDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("The email of employee cannot be updated.");
        verify(employeeRepository, times(1)).findById(mockEmployeeDTO.getId());
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void testUpdateEmployee_whenValidEmployee_thenUpdateEmployee() {
        /* Arrange */
        when(employeeRepository.findById(mockEmployeeDTO.getId())).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDTO.setName("John");
        mockEmployeeDTO.setSalary(199L);
        Employee newEmployee = modelMapper.map(mockEmployeeDTO, Employee.class);
        when(employeeRepository.save(any(Employee.class))).thenReturn(newEmployee);

        /* Act */
        EmployeeDTO updatedEmployeeDTO = employeeServiceImplementation.updateEmployee(mockEmployeeDTO.getId(), mockEmployeeDTO);
        /* Assert */
        assertThat(updatedEmployeeDTO).isEqualTo(mockEmployeeDTO);
        verify(employeeRepository).findById(1L);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void testDeleteEmployee_whenEmployeeDoesNotExist_thenThrowException() {
        /* Arrange */
        when(employeeRepository.existsById(1L)).thenReturn(false);
        /* Act & Assert */
        assertThatThrownBy(() -> employeeServiceImplementation.deleteEmployee(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No Employee found with ID 1");
        verify(employeeRepository, never()).deleteById(anyLong());
    }

    @Test
    void testDeleteEmployee_whenEmployeeIsValid_thenDeleteEmployee() {
        /* Arrange */
        when(employeeRepository.existsById(1L)).thenReturn(true);

        /* Act */
        assertThatCode(() -> employeeServiceImplementation.deleteEmployee(1L))
                .doesNotThrowAnyException();
        /* Assert */
        verify(employeeRepository, times(1)).deleteById(1L);
    }

}