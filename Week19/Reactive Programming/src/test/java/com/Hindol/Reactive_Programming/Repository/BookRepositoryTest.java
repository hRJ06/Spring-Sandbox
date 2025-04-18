package com.Hindol.Reactive_Programming.Repository;

import com.Hindol.Reactive_Programming.Entity.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.test.StepVerifier;

import java.util.List;

@DataR2dbcTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        var books = List.of(new Book(null, "Java", "Basic of Java", 1L));
        bookRepository.saveAll(books).blockLast();
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll().block();
    }

    @Test
    void findAll() {
        var booksFlux = bookRepository.findAll().log();
        StepVerifier.create(booksFlux)
                .expectNextCount(1)
                .verifyComplete();
    }
}