package com.Hindol.Week3HW.Service.Implementation;

import com.Hindol.Week3HW.DTO.AuthorDTO;
import com.Hindol.Week3HW.Entity.AuthorEntity;
import com.Hindol.Week3HW.Exception.ResourceNotFoundException;
import com.Hindol.Week3HW.Repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplementationTest {
    @Mock
    private AuthorRepository authorRepository;

    @Spy
    private ModelMapper modelMapper;

    private AuthorEntity mockAuthor;
    private AuthorDTO mockAuthorDTO;


    @InjectMocks
    private AuthorServiceImplementation authorServiceImplementation;

    @BeforeEach
    void setUp() {
        mockAuthor = AuthorEntity.builder()
                .id(1L)
                .name("Hindol Roy")
                .build();
        mockAuthorDTO = AuthorDTO.builder()
                .id(1L)
                .name("Hindol Roy")
                .build();
    }

    @Test
    void testGetAllAuthors_whenAuthorsArePresent_thenReturnListOfAuthorDTOs() {
        /* Arrange */
        when(authorRepository.findAll()).thenReturn(List.of(mockAuthor));
        /* Act */
        List<AuthorDTO> authorDTOS = authorServiceImplementation.getAllAuthors();
        /* Assert */
        assertThat(authorDTOS).isNotNull();
        assertThat(authorDTOS).hasSize(1);
        assertThat(authorDTOS.get(0).getName()).isEqualTo(mockAuthor.getName());
        verify(authorRepository, only()).findAll();
    }

    @Test
    void testGetAuthorById_whenAuthorsIdIsPresent_thenReturnAuthorDTO() {
        /* Arrange */
        when(authorRepository.existsById(anyLong())).thenReturn(true);
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(mockAuthor));
        /* Act */
        AuthorDTO authorDTO = authorServiceImplementation.getAuthorById(1L);
        /* Assert */
        assertThat(authorDTO).isNotNull();
        assertThat(authorDTO.getId()).isEqualTo(mockAuthor.getId());
        verify(authorRepository, times(1)).existsById(1L);
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAuthorById_whenAuthorIdIsNotPresent_thenThrowException() {
        /* Arrange */
        when(authorRepository.existsById(anyLong())).thenReturn(false);
        /* Act & Assert */
        assertThatThrownBy(() -> authorServiceImplementation.getAuthorById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No Author found with ID : 1");
        verify(authorRepository, times(1)).existsById(1L);
        verify(authorRepository, never()).findById(1L);
    }

    @Test
    void testCreateAuthor_wheValidAuthor_thenCreateNewAuthor() {
        /* Arrange */
        when(authorRepository.save(any(AuthorEntity.class))).thenReturn(mockAuthor);
        /* Act */
        AuthorDTO createdAuthorDTO = authorServiceImplementation.createAuthor(mockAuthorDTO);
        /* Assert */
        assertThat(createdAuthorDTO).isNotNull();
        assertThat(createdAuthorDTO.getName()).isEqualTo(mockAuthor.getName());

        ArgumentCaptor<AuthorEntity> authorEntityArgumentCaptor = ArgumentCaptor.forClass(AuthorEntity.class);
        verify(authorRepository, times(1)).save(authorEntityArgumentCaptor.capture());
        assertThat(authorEntityArgumentCaptor.getValue().getName()).isEqualTo(mockAuthor.getName());
    }

    @Test
    void testUpdateAuthorById_whenValidIdIsGiven_thenReturnUpdatedAuthor() {
        /* Arrange */
        when(authorRepository.existsById(anyLong())).thenReturn(true);
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(mockAuthor));
        mockAuthor.setName("John Roy");
        when(authorRepository.save(any(AuthorEntity.class))).thenReturn(mockAuthor);

        Map<String, Object> map = new HashMap<>() {{
            put("name", "John Roy");
        }};

        /* Act */
        AuthorDTO updatedAuthorDTO = authorServiceImplementation.updateAuthorById(1L, map);

        /* Assert */
        assertThat(updatedAuthorDTO).isNotNull();
        assertThat(updatedAuthorDTO.getName()).isEqualTo(mockAuthor.getName());

        ArgumentCaptor<AuthorEntity> authorEntityArgumentCaptor = ArgumentCaptor.forClass(AuthorEntity.class);
        verify(authorRepository, times(1)).existsById(1L);
        verify(authorRepository, times(1)).save(authorEntityArgumentCaptor.capture());
        assertThat(authorEntityArgumentCaptor.getValue().getName()).isEqualTo(mockAuthor.getName());
    }

    @Test
    void testUpdateAuthorById_whenInvalidIsGiven_thenThrowException() {
        /* Arrange */
        when(authorRepository.existsById(1L)).thenReturn(false);
        Map<String, Object> map = new HashMap<>() {{
            put("name", "John Roy");
        }};
        /* Act & Assert */
        assertThatThrownBy(() -> authorServiceImplementation.updateAuthorById(1L, map))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No Author found with ID : 1");
        verify(authorRepository, times(1)).existsById(1L);
        verify(authorRepository, never()).save(any(AuthorEntity.class));
    }

    @Test
    void testDeleteAuthorById_whenValidIdIsGiven_thenDeleteEmployee() {
        /* Arrange */
        when(authorRepository.existsById(1L)).thenReturn(true);
        /* Act & Assert */
        assertThatCode(() -> authorServiceImplementation.deleteAuthorById(1L))
                .doesNotThrowAnyException();
        verify(authorRepository,times(1)).existsById(1L);
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAuthorById_whenInvalidIdIsGiven_thenThrowException() {
        /* Arrange */
        when(authorRepository.existsById(1L)).thenReturn(false);
        /* Act & Assert */
        assertThatThrownBy(() -> authorServiceImplementation.deleteAuthorById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No Author found with ID : 1");
        verify(authorRepository,times(1)).existsById(1L);
        verify(authorRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetAuthorsByName_whenValidNameIsGiven_thenReturnListOfAuthors() {
        /* Arrange */
        when(authorRepository.findByNameLike(anyString())).thenReturn(List.of(mockAuthor));
        String searchQuery = "ol";
        /* Act */
        List<AuthorDTO> authorDTOS = authorServiceImplementation.getAuthorsByName(searchQuery);
        /* Assert */
        assertThat(authorDTOS).isNotNull();
        assertThat(authorDTOS).hasSize(1);
        verify(authorRepository, only()).findByNameLike('%' + searchQuery + '%');
    }
}