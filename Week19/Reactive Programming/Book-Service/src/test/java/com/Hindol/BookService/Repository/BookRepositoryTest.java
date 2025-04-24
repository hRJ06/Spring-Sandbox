package com.Hindol.BookService.Repository;

import com.Hindol.BookService.Entity.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataR2dbcTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        var books = List.of(new Book(null, "Java", "Basic of Java", 1L),
                            new Book(null, "Python", "Basic of Python", 1L)
                            );
        bookRepository.saveAll(books).blockLast();
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll().block();
    }

    @Test
    void findAll() {
        /* Act */
        var booksFlux = bookRepository.findAll().log();
        /* Assert */
        StepVerifier.create(booksFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void saveBook() {
        /* Arrange */
        var bookInfo = new Book(null, "C++", "Basic of C++", 1L);

        /* Act */
        var bookInfoMono = bookRepository.save(bookInfo);

        /* Assert */
        StepVerifier.create(bookInfoMono).
                assertNext(book -> {
                    assertNotNull(book.getId());
                    assertEquals("Basic of C++", book.getDescription());
                })
                .verifyComplete();
    }

    @Test
    void findById() {
        /* Arrange */
        var savedBookInfo = bookRepository.save(new Book(null, "C++", "Basic of C++", 1L)).block();
        Long id = savedBookInfo.getId();

        /* Act */
        var bookInfoMono = bookRepository.findById(id).log();

        /* Assert */
        StepVerifier.create(bookInfoMono)
                .assertNext(bookInfo -> {
                    assertEquals("Basic of C++", bookInfo.getDescription());
                })
                .verifyComplete();
    }

    @Test
    void updateBook() {
        /* Arrange */
        var savedBookInfo = bookRepository.save(new Book(null, "C++", "Basic of C++", 1L)).block();
        Long id = savedBookInfo.getId();
        var bookInfo = bookRepository.findById(id).block();

        /* Act */
        bookInfo.setDescription("Ultimate Guide to Java");
        var bookInfoMono = bookRepository.save(bookInfo).log();

        /* Assert */
        StepVerifier.create(bookInfoMono)
                .assertNext(book -> {
                    assertEquals("Ultimate Guide to Java", book.getDescription());
                })
                .verifyComplete();
    }

    @Test
    void deleteBook() {
        /* Arrange */
        var savedBookInfo = bookRepository.save(new Book(null, "C++", "Basic of C++", 1L)).block();
        Long id = savedBookInfo.getId();

        /* Act */
        bookRepository.deleteById(id).block();
        var bookInfoFlux = bookRepository.findAll().log();

        /* Assert */
        StepVerifier.create(bookInfoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}