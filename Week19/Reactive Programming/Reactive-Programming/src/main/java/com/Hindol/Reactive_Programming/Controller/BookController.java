package com.Hindol.Reactive_Programming.Controller;

import com.Hindol.Reactive_Programming.Entity.Book;
import com.Hindol.Reactive_Programming.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("")
    public Flux<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
}
