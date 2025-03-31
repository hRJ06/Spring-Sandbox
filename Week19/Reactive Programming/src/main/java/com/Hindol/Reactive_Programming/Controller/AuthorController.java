package com.Hindol.Reactive_Programming.Controller;

import com.Hindol.Reactive_Programming.DTO.AuthorDTO;
import com.Hindol.Reactive_Programming.Entity.Author;
import com.Hindol.Reactive_Programming.Service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    @PostMapping("")
    public Mono<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        return authorService.createBook(authorDTO);
    }
}
