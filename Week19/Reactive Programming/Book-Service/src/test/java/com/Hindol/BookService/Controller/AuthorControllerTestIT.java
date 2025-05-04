package com.Hindol.BookService.Controller;

import com.Hindol.BookService.DTO.AuthorDTO;
import com.Hindol.BookService.Repository.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class AuthorControllerTestIT extends AbstractITTest {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    private AuthorRepository authorRepository;

    @AfterEach
    void tearDown() {
        authorRepository.deleteAll().block();
    }

    private static final String BOOK_ENDPOINT = "/api/v1/book-service/author";

    @Test
    void createAuthor() {
        /* Arrange */
        var authorInfo = new AuthorDTO(null, "Hindol Roy", "91 6289632205");
        /* Act & Assert */
        webTestClient.post()
                .uri(BOOK_ENDPOINT)
                .bodyValue(authorInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(AuthorDTO.class)
                .consumeWith(authorDTOEntityExchangeResult -> {
                    var authorInfoResponse = authorDTOEntityExchangeResult.getResponseBody();
                    assertNotNull(authorInfoResponse.getId());
                    assertEquals("91 6289632205", authorInfo.getPhoneNo());
                });
    }
}