package com.Hindol.Reactive_Programming.Controller;

import com.Hindol.Reactive_Programming.DTO.BookDTO;
import com.Hindol.Reactive_Programming.Entity.Book;
import com.Hindol.Reactive_Programming.Repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BookControllerTestIT {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll().block();
    }

    private static final String BOOK_ENDPOINT = "/api/v1/book";

    @Test
    void createBook() {
        /* Arrange */
        var bookInfo = new BookDTO(null, "C++", "Basic of C++", 1L);
        /* Act & Assert */
        webTestClient.post()
                .uri(BOOK_ENDPOINT + "/{authorId}", 1)
                .bodyValue(bookInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(BookDTO.class)
                .consumeWith(bookDTOResult -> {
                    var bookDTO = bookDTOResult.getResponseBody();
                    assertNotNull(bookDTO);
                    assertNotNull(bookDTO.getId());
                    assertEquals("Basic of C++", bookDTO.getDescription());
                });
    }

    @Test
    void getAllBooks() {
        /* Arrange */
        var books = List.of(new Book(null, "Java", "Basic of Java", 1L),
                new Book(null, "Python", "Basic of Python", 1L)
        );
        bookRepository.saveAll(books).blockLast();
        /* Act & Assert */
        webTestClient.get()
                .uri(BOOK_ENDPOINT)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(BookDTO.class)
                .hasSize(2);
    }

    @Test
    void getBookById()  {
        /* Arrange */
        var bookInfo = new Book(null, "Java", "Basic of Java", 1L);
        var savedBook = bookRepository.save(bookInfo).block();
        Long id = savedBook.getId();

        /* Act & Assert */
        webTestClient.get()
                .uri(BOOK_ENDPOINT + "/{bookId}", id)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(BookDTO.class)
                .consumeWith(bookDTOEntityExchangeResult -> {
                    var bookDTOResponse = bookDTOEntityExchangeResult.getResponseBody();
                    assertNotNull(bookDTOResponse);
                    assertEquals(id, bookDTOResponse.getId());
                });
    }

    @Test
    void updateBookById() {
        /* Arrange */
        var bookInfo = new Book(null, "Java", "Basic of Java", 1L);
        var savedBook = bookRepository.save(bookInfo).block();
        Long id = savedBook.getId();
        var newBookInfo = new Book(null, "Guide to Java", "Basic of Java", 1L);

        /* Act & Assert */
        webTestClient.put()
                .uri(BOOK_ENDPOINT + "/{bookId}", id)
                .bodyValue(newBookInfo)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(BookDTO.class)
                .consumeWith(bookDTOEntityExchangeResult -> {
                    var bookDTOResponse = bookDTOEntityExchangeResult.getResponseBody();
                    assertNotNull(bookDTOResponse);
                    assertEquals(id, bookDTOResponse.getId());
                    assertEquals("Guide to Java", bookDTOResponse.getName());
                });
    }

    @Test
    void deleteBookById() {
        /* Arrange */
        var bookInfo = new Book(null, "Java", "Basic of Java", 1L);
        var savedBook = bookRepository.save(bookInfo).block();
        Long id = savedBook.getId();

        /* Act & Assert */
        webTestClient.delete()
                .uri(BOOK_ENDPOINT + "/{bookId}", id)
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}