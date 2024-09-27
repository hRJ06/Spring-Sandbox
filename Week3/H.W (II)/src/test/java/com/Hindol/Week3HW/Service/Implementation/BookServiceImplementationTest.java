package com.Hindol.Week3HW.Service.Implementation;

import com.Hindol.Week3HW.DTO.BookDTO;
import com.Hindol.Week3HW.Entity.AuthorEntity;
import com.Hindol.Week3HW.Entity.BookEntity;
import com.Hindol.Week3HW.Exception.ResourceNotFoundException;
import com.Hindol.Week3HW.Repository.AuthorRepository;
import com.Hindol.Week3HW.Repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplementationTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private BookServiceImplementation bookServiceImplementation;

    private BookEntity mockBookEntity;
    private BookDTO mockBookDTO;
    private AuthorEntity mockAuthor;

    @BeforeEach
    void setUp() {
        mockBookEntity = BookEntity.builder()
                .id(1L)
                .title("Charlie Chaplin")
                .description("A book on Charlie Chaplin")
                .publishDate(LocalDate.of(2023, 5, 16))
                .build();
        mockBookDTO = BookDTO.builder()
                .id(1L)
                .title("Charlie Chaplin")
                .description("A book on Charlie Chaplin")
                .publishDate(LocalDate.of(2023, 5, 16))
                .build();
        mockAuthor = AuthorEntity.builder()
                .id(1L)
                .name("Hindol Roy")
                .build();
    }

    @Test
    void testGetBookById_whenGivenValidBookId_thenReturnBookDTO() {
        /* Arrange */
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(mockBookEntity));
        /* Act */
        BookDTO result = bookServiceImplementation.getBookById(1L);
        assertThat(result).isNotNull();
        /* Assertion */
        assertThat(result.getTitle()).isEqualTo(mockBookEntity.getTitle());
        assertThat(result.getDescription()).isEqualTo(mockBookEntity.getDescription());
        assertThat(result.getPublishDate()).isEqualTo(mockBookEntity.getPublishDate());
        verify(bookRepository).findById(1L);
    }

    @Test
    void testGetBookById_whenGivenInvalidBookId_thenThrowException() {
        /* Arrange */
        when(bookRepository.existsById(1L)).thenReturn(false);
        /* Act & Assert */
        assertThatCode(() -> bookServiceImplementation.getBookById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No Book found with ID : 1");
        verify(bookRepository, only()).existsById(1L);
        verify(bookRepository, never()).findById(1L);
    }

    @Test
    void testGetAllBooks_whenBooksArePresent_thenReturnListOfBookDTOs() {
        /* Arrange */
        when(bookRepository.findAll()).thenReturn(List.of(mockBookEntity));
        /* Act */
        List<BookDTO> bookDTOList = bookServiceImplementation.getAllBooks();
        /* Assert */
        assertThat(bookDTOList).isNotNull();
        assertThat(bookDTOList).hasSize(1);
        assertThat(bookDTOList.get(0).getTitle()).isEqualTo(mockBookDTO.getTitle());
        verify(bookRepository, only()).findAll();
    }

    @Test
    void testCreateBook_whenValidBookDTO_thenReturnSavedBookDTO() {
        /* Arrange */
        when(bookRepository.save(any(BookEntity.class))).thenReturn(mockBookEntity);
        /* Act */
        BookDTO createdBookDTO = bookServiceImplementation.createBook(mockBookDTO);
        /* Assert */
        assertThat(createdBookDTO).isNotNull();
        assertThat(createdBookDTO.getTitle()).isEqualTo(mockBookDTO.getTitle());
        ArgumentCaptor<BookEntity> bookEntityArgumentCaptor = ArgumentCaptor.forClass(BookEntity.class);
        verify(bookRepository).save(bookEntityArgumentCaptor.capture());
    }
    @Test
    void testUpdateBookById_whenValidBookIdIsGiven_ThenReturnUpdatedBookDTO() {
        /* Arrange */
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(mockBookEntity));

        mockBookEntity.setTitle("Chaplin Time");
        mockBookEntity.setDescription("The best book on Charlie Chaplin");

        when(bookRepository.save(any(BookEntity.class))).thenReturn(mockBookEntity);

        Map<String, Object> map = new HashMap<>() {{
            put("title", "Chaplin Time");
            put("description", "The best book on Charlie Chaplin");
        }};

        /* Act */
        BookDTO updatedBookDTO = bookServiceImplementation.updatedBookById(1L,map);

        /* Assert */
        ArgumentCaptor<BookEntity> argumentCaptor = ArgumentCaptor.forClass(BookEntity.class);
        assertThat(updatedBookDTO).isNotNull();

        /* Checking on the updated Book DTO */
        assertThat(updatedBookDTO).isNotNull();
        assertThat(updatedBookDTO.getTitle()).isEqualTo(mockBookEntity.getTitle());
        assertThat(updatedBookDTO.getDescription()).isEqualTo(mockBookEntity.getDescription());
        verify(bookRepository, times(1)).findById(1L);

        /* Checking on the BookEntity passed to save of repository */
        verify(bookRepository, times(1)).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getTitle()).isEqualTo(mockBookEntity.getTitle());
        assertThat(argumentCaptor.getValue().getDescription()).isEqualTo(mockBookEntity.getDescription());
    }

    @Test
    void testUpdateBookById_whenInvalidBookIdIsGiven_thenThrowException() {
        /* Arrange */
        when(bookRepository.existsById(1L)).thenReturn(false);
        Map<String, Object> map = new HashMap<>() {{
            put("title", "Chaplin Time");
            put("description", "The best book on Charlie Chaplin");
        }};

        /* Act */
        assertThatThrownBy(() -> {
            bookServiceImplementation.updatedBookById(1L, map);
        })
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No Book found with ID : 1");

        /* Assert */
        verify(bookRepository, never()).findById(1L);
        verify(bookRepository, never()).save(any(BookEntity.class));
    }


    @Test
    void testDeleteBookById_whenValidIdIsGiven_thenDeleteBook() {
        /* Arrange */
        when(bookRepository.existsById(1L)).thenReturn(true);
        /* Act & Assert */
        assertThatCode(() -> {
            bookServiceImplementation.deleteBookById(1L);
        })
                .doesNotThrowAnyException();
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBookById_whenInvalidIdIsGiven_thenThrowException() {
        /* Arrange */
        when(bookRepository.existsById(1L)).thenReturn(false);
        /* Act & Assert */
        assertThatThrownBy(() -> {
            bookServiceImplementation.deleteBookById(1L);
        })
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No Book found with ID : 1");
        verify(bookRepository, never()).deleteById(1L);
    }

    @Test
    void testGetBooksPublishedAfterDate_whenBooksArePresent_thenReturnListOfBookDTOs() {
        /* Arrange */
        LocalDate date = LocalDate.of(2022, 2, 2);
        when(bookRepository.findByPublishDateAfter(date)).thenReturn(List.of(mockBookEntity));
        /* Act */
        List<BookDTO> bookDTOList = bookServiceImplementation.getBooksPublishedAfterDate(date);
        /* Assert */
        assertThat(bookDTOList).isNotNull();
        assertThat(bookDTOList).hasSize(1);
        assertThat(bookDTOList.get(0).getTitle()).isEqualTo(mockBookDTO.getTitle());
        verify(bookRepository, only()).findByPublishDateAfter(date);
    }

    @Test
    void testGetBooksByTitle_whenBooksArePresent_thenReturnListOfBookDTOs() {
        /* Arrange */
        String title = "Chaplin";
        when(bookRepository.findByTitleLike('%' + title + '%')).thenReturn(List.of(mockBookEntity));
        /* Act */
        List<BookDTO> bookDTOList = bookServiceImplementation.getBooksByTitle(title);
        /* Assert */
        assertThat(bookDTOList).isNotNull();
        assertThat(bookDTOList).hasSize(1);
        assertThat(bookDTOList.get(0).getTitle()).isEqualTo(mockBookDTO.getTitle());
        verify(bookRepository, only()).findByTitleLike('%' + title + '%');
    }

    @Test
    void testGetBooksByAuthor_whenAuthorExists_thenReturnListOfBookDTOs() {
        /* Arrange */
        mockAuthor.setBooks(new ArrayList<>());
        mockAuthor.getBooks().add(mockBookEntity);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(mockAuthor));
        /* Act */
        List<BookDTO> bookDTOList = bookServiceImplementation.getBooksByAuthor(1L);
        /* Assert */
        assertThat(bookDTOList).isNotNull();
        assertThat(bookDTOList).hasSize(1);
        assertThat(bookDTOList.get(0).getTitle()).isEqualTo(mockBookDTO.getTitle());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBooksByAuthor_whenAuthorDoesNotExists_thenThrowException() {
        /* Arrange */
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        /* Act & Assert */
        assertThatThrownBy(() -> bookServiceImplementation.getBooksByAuthor(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No Author found with ID : 1");
        verify(authorRepository, only()).findById(1L);
    }

    @Test
    void testAssignAuthorToBook_whenBookAndAuthorExists_thenReturnBookDTO() {
        /* Arrange */
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(mockAuthor));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(mockBookEntity));

        mockBookEntity.setAuthor(mockAuthor);
        when(bookRepository.save(mockBookEntity)).thenReturn(mockBookEntity);

        /* Act */
        BookDTO authorAssignedBookDTO = bookServiceImplementation.assignAuthorToBook(1L, 1L);
        /* Assert */
        assertThat(authorAssignedBookDTO).isNotNull();
        assertThat(authorAssignedBookDTO.getTitle()).isEqualTo(mockBookEntity.getTitle());
        assertThat(authorAssignedBookDTO.getAuthor().getId()).isEqualTo(mockAuthor.getId());
        assertThat(authorAssignedBookDTO.getAuthor().getName()).isEqualTo(mockAuthor.getName());
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(mockBookEntity);
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testAssignAuthorToBook_whenAuthorDoesNotExist_thenThrowException() {
        /* Arrange */
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        /* Act */
        assertThatThrownBy(() -> bookServiceImplementation.assignAuthorToBook(1L, 1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Author not found with ID : 1");
        /* Assert */
        verify(bookRepository, times(1)).existsById(1L);
        verify(authorRepository, times(1)).findById(1L);
        verify(bookRepository, never()).findById(1L);
        verify(bookRepository, never()).save(any(BookEntity.class));
    }

    @Test
    void testAssignAuthorToBook_whenBookDoesNotExist_thenReturnException() {
        /* Arrange */
        when(bookRepository.existsById(1L)).thenReturn(false);
        /* Act */
        assertThatThrownBy(() -> bookServiceImplementation.assignAuthorToBook(1L, 1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No Book found with ID : 1");
        /* Assert */
        verify(bookRepository, times(1)).existsById(1L);
        verify(authorRepository, never()).findById(1L);
        verify(bookRepository, never()).findById(1L);
        verify(bookRepository, never()).save(any(BookEntity.class));
    }

}