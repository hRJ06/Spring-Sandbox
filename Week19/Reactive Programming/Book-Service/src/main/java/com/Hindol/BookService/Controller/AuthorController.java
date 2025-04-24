package com.Hindol.BookService.Controller;

import com.Hindol.BookService.DTO.AuthorDTO;
import com.Hindol.BookService.Service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/book-service/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        return authorService.createBook(authorDTO);
    }
}
