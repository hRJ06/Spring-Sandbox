package com.Hindol.BookService.Service.Implementation;

import com.Hindol.BookService.DTO.AuthorDTO;
import com.Hindol.BookService.Mapper.AuthorMapper;
import com.Hindol.BookService.Repository.AuthorRepository;
import com.Hindol.BookService.Service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImplementation implements AuthorService {

    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;
    @Override
    public Mono<AuthorDTO> createBook(AuthorDTO authorDTO) {
        return Mono.just(authorMapper.dtoToEntity(authorDTO))
                .flatMap(authorRepository::save)
                .map(authorMapper::entityToDto)
                .onErrorResume(e -> {
                    log.error("Error creating author - {}", e.getLocalizedMessage());
                    return Mono.empty();
                });
    }
}
