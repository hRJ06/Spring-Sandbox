package com.Hindol.BookService.Controller;

import com.Hindol.BookService.DTO.BookDTO;
import com.Hindol.BookService.DTO.ReviewDTO;
import com.Hindol.BookService.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/book-service")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("")
    public Flux<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/{authorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookDTO> createBook(@RequestBody BookDTO bookDTO, @PathVariable("authorId") Long authorId) {
        return bookService.createBook(bookDTO, authorId);
    }

    @GetMapping("/{bookId}")
    public Mono<BookDTO> getBookById(@PathVariable("bookId") Long bookId) {
        return bookService.getBook(bookId);
    }

    @GetMapping("/review/{bookId}")
    public Mono<ReviewDTO> getReview(@PathVariable("bookId") Long bookId) { return bookService.getReview(bookId); }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBookById(@PathVariable("bookId") Long bookId) {
        return bookService.deleteBook(bookId);
    }

    @PutMapping("/{bookId}")
    public Mono<BookDTO> updateBookById(@PathVariable("bookId") Long bookId, @RequestBody BookDTO bookDTO) {
        return bookService.updateBook(bookId, bookDTO);
    }
}
