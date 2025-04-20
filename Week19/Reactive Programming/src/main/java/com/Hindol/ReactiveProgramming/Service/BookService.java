package com.Hindol.ReactiveProgramming.Service;

import com.Hindol.ReactiveProgramming.DTO.BookDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
   Flux<BookDTO> getAllBooks();
   Mono<BookDTO> createBook(BookDTO bookDTO, Long authorId);
   Mono<BookDTO> getBook(Long bookId);
   Mono<BookDTO> updateBook(Long bookId, BookDTO bookDTO);
   Mono<Void> deleteBook(Long bookId);
}
