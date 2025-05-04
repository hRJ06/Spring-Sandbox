package com.Hindol.BookService.Controller;

import com.Hindol.BookService.DTO.BookDTO;
import com.Hindol.BookService.DTO.ReviewDTO;
import com.Hindol.BookService.Entity.Book;
import com.Hindol.BookService.Repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureWireMock(port = 8084)
@TestPropertySource (
        properties = {
                "restClient.review-service-url=http://localhost:8084/api/v1/review-service"
        }
)
class BookControllerTestIT extends AbstractITTest {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll().block();
    }

    private static final String BOOK_ENDPOINT = "/api/v1/book-service";

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
        stubFor(delete(urlEqualTo("/api/v1/review-service/book/" + id))
                .willReturn(aResponse()
                        .withStatus(204))
        );


        /* Act & Assert */
        webTestClient.delete()
                .uri(BOOK_ENDPOINT + "/{bookId}", id)
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    void getReview() {
        /* Arrange */
        var bookInfo = new Book(null, "Java", "Basic of Java", 1L);
        var savedBook = bookRepository.save(bookInfo).block();
        Long id = savedBook.getId();
        stubFor(get(urlEqualTo("/api/v1/review-service/book/" + id))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("review.json"))
        );

        /* Act */
        webTestClient.get()
                .uri(BOOK_ENDPOINT + "/{bookId}/reviews", id)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ReviewDTO.class)
                .hasSize(3);
    }
}