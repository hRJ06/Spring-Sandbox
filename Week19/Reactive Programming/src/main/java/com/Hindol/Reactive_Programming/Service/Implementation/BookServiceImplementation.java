package com.Hindol.Reactive_Programming.Service.Implementation;

import com.Hindol.Reactive_Programming.DTO.BookDTO;
import com.Hindol.Reactive_Programming.Entity.Book;
import com.Hindol.Reactive_Programming.Exception.ResourceNotFoundException;
import com.Hindol.Reactive_Programming.Mapper.BookMapper;
import com.Hindol.Reactive_Programming.Repository.BookRepository;
import com.Hindol.Reactive_Programming.Service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImplementation implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Flux<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .map(bookMapper::entityToDto);
    }

    @Override
    public Mono<BookDTO> createBook(BookDTO bookDTO, Long authorId) {
        return Mono
                .just(bookMapper.dtoToEntity(bookDTO))
                .doOnNext(book -> book.setAuthorId(authorId))
                .flatMap(bookRepository::save)
                .map(bookMapper::entityToDto)
                .onErrorResume(e -> {
                    log.error("Error occurred while saving book : {}", e.getMessage());
                    return Mono.empty();
                });
    }

    @Override
    public Mono<BookDTO> getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Book", "ID", String.valueOf(bookId))))
                .map(bookMapper::entityToDto);
    }

    @Override
    public Mono<BookDTO> updateBook(Long bookId, BookDTO bookDTO) {
        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Book", "ID", String.valueOf(bookId))))
                .flatMap(book -> {
                    book.setName(bookDTO.getName());
                    book.setDescription(bookDTO.getDescription());
                    return bookRepository.save(book);
                })
                .map(bookMapper::entityToDto);
    }

    @Override
    public Mono<Void> deleteBook(Long bookId) {
        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Book", "ID", String.valueOf(bookId))))
                .flatMap(bookRepository::delete);
    }
}
