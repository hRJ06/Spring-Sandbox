package com.Hindol.Reactive_Programming.Controller;

import com.Hindol.Reactive_Programming.DTO.BookDTO;
import com.Hindol.Reactive_Programming.Entity.Book;
import com.Hindol.Reactive_Programming.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("")
    public Flux<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/{authorId}")
    public Mono<BookDTO> createBook(@RequestBody BookDTO bookDTO, @PathVariable("authorId") Long authorId) {
        return bookService.createBook(bookDTO, authorId);
    }
}
