package com.Hindol.Reactive_Programming.Service.Implementation;

import com.Hindol.Reactive_Programming.Entity.Book;
import com.Hindol.Reactive_Programming.Repository.BookRepository;
import com.Hindol.Reactive_Programming.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class BookServiceImplementation implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
