package com.Hindol.Reactive_Programming.Service;

import com.Hindol.Reactive_Programming.Entity.Book;
import reactor.core.publisher.Flux;

public interface BookService {
   Flux<Book> getAllBooks();
}
