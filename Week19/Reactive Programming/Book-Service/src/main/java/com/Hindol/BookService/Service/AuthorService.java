package com.Hindol.BookService.Service;

import com.Hindol.BookService.DTO.AuthorDTO;
import reactor.core.publisher.Mono;

public interface AuthorService {
    Mono<AuthorDTO> createBook(AuthorDTO authorDTO);
}
