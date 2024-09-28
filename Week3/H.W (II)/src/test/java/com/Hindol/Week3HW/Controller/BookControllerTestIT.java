package com.Hindol.Week3HW.Controller;

import com.Hindol.Week3HW.DTO.AuthorDTO;
import com.Hindol.Week3HW.DTO.BookDTO;
import com.Hindol.Week3HW.Entity.AuthorEntity;
import com.Hindol.Week3HW.Entity.BookEntity;
import com.Hindol.Week3HW.Repository.AuthorRepository;
import com.Hindol.Week3HW.Repository.BookRepository;
import com.Hindol.Week3HW.TestContainerConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
class BookControllerTestIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private AuthorEntity mockAuthor;
    private AuthorDTO mockAuthorDTO;
    private BookEntity mockBook;
    private BookDTO mockBookDTO;
    private BookDTO invalidMockBookDTO;

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
        mockBook = BookEntity.builder()
                .id(1L)
                .title("Charlie Chaplin")
                .description("Classics of Charlie Chaplin")
                .build();
        mockBookDTO = BookDTO.builder()
                .id(1L)
                .title("Charlie Chaplin")
                .description("Classics of Charlie Chaplin")
                .publishDate(LocalDate.of(2023, 2, 2))
                .build();
        invalidMockBookDTO = BookDTO.builder()
                .id(1L)
                .title("Charlie Chaplin")
                .description("Classics of CC")
                .publishDate(LocalDate.of(2024, 10, 2))
                .build();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void testGetAllBooks_whenBookPresent_thenReturnListOfBooks() {
        BookEntity savedBook = bookRepository.save(mockBook);
        webTestClient.get()
                .uri("/book")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data").isArray()
                .jsonPath("$.data[0].id").isEqualTo(savedBook.getId())
                .jsonPath("$.data[0].title").isEqualTo(savedBook.getTitle())
                .jsonPath("$.data[0].description").isEqualTo(savedBook.getDescription());
    }

    @Test
    void testGetAllBooks_whenBookNotPresent_thenReturnEmptyListOfBooks() {
        webTestClient.get()
                .uri("/book")
                .exchange()
                .expectStatus().isOk()
                .expectBody().
                jsonPath("$.data").isEmpty();
    }

    @Test
    void testGetBookById_whenBookExist_thenReturnBook() {
        BookEntity savedBook = bookRepository.save(mockBook);
        webTestClient.get()
                .uri("/book/{id}", savedBook.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(savedBook.getId())
                .jsonPath("$.data.title").isEqualTo(savedBook.getTitle())
                .jsonPath("$.data.description").isEqualTo(savedBook.getDescription());
    }

    @Test
    void testGetBookById_whenBookDoesNotExists_thenThrowException() {
        webTestClient.get()
                .uri("/book/{id}", mockBook.getId())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateBook_whenValidBookDTO_thenReturnBookDTO() {
        webTestClient.post()
                .uri("/book")
                .bodyValue(mockBookDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.data.title").isEqualTo(mockBook.getTitle())
                .jsonPath("$.data.description").isEqualTo(mockBook.getDescription());
    }

    @Test
    void testCreateBook_whenInvalidBookDTO_thenThrowException() {
        webTestClient.post()
                .uri("/book")
                .bodyValue(invalidMockBookDTO)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.apiError.subErrors[0]").isEqualTo("Book publish date should be Past or Present");
    }

    @Test
    void testUpdateBook_whenValidBookGiven_thenReturnUpdatedBookDTO() {
        BookEntity savedBook = bookRepository.save(mockBook);
        Map<String, Object> map = new HashMap<>() {{
            put("title", "Updated Title");
            put("description","Updated description");
        }};
        webTestClient.put()
                .uri("/book/{id}", savedBook.getId())
                .bodyValue(map)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.title").isEqualTo(map.get("title"))
                .jsonPath("$.data.description").isEqualTo(map.get("description"));
    }

    @Test
    void testUpdateBook_whenInvalidBookGiven_thenThrowException() {
        Map<String, Object> map = new HashMap<>() {{
            put("title", "Updated Title");
            put("description","Updated description");
        }};
        webTestClient.put()
                .uri("/book/{id}", mockBook.getId())
                .bodyValue(map)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.apiError.message").isEqualTo("No Book found with ID : 1");
    }

    @Test
    void testDeleteBookById_whenValidBookIsGiven_thenDeleteBook() {
        BookEntity savedBook = bookRepository.save(mockBook);
        webTestClient.delete()
                .uri("/book/{id}", savedBook.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testDeleteBookById_whenInvalidBookIsGiven_thenThrowException() {
        webTestClient.delete()
                .uri("/book/{id}", mockBook.getId())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.apiError.message").isEqualTo("No Book found with ID : 1");
    }
}