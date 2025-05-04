package com.Hindol.BookService.Repository;

import com.Hindol.BookService.Entity.Author;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;


class AuthorRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @AfterEach
    void tearDown() {
        authorRepository.deleteAll().block();
    }

    @Test
    void save() {
        /* Arrange */
        var authorInfo = new Author(null, "Hindol Roy", "91 6289632205");

        /* Act */
        var authorInfoMono = authorRepository.save(authorInfo);

        /* Assert */
        StepVerifier.create(authorInfoMono).
                assertNext(author -> {
                    assertNotNull(author.getId());
                    assertEquals("91 6289632205", author.getPhoneNo());
                })
                .verifyComplete();
    }
}