package com.Hindol.ReactiveProgramming.Service;

import com.Hindol.ReactiveProgramming.DTO.AuthorDTO;
import reactor.core.publisher.Mono;

public interface AuthorService {
    Mono<AuthorDTO> createBook(AuthorDTO authorDTO);
}
