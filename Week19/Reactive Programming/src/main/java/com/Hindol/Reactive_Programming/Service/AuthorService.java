package com.Hindol.Reactive_Programming.Service;

import com.Hindol.Reactive_Programming.DTO.AuthorDTO;
import reactor.core.publisher.Mono;

public interface AuthorService {
    Mono<AuthorDTO> createBook(AuthorDTO authorDTO);
}
