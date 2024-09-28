package com.Hindol.Week3HW.Controller;

import com.Hindol.Week3HW.DTO.AuthorDTO;
import com.Hindol.Week3HW.Service.AuthorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;
    Logger log = LoggerFactory.getLogger(AuthorController.class);

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthor(@RequestParam(required = false) String name) {
        if(name != null && !name.isEmpty()) return ResponseEntity.ok(authorService.getAuthorsByName(name));
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long authorId) {
        log.info("AUTHOR ID : {}", authorId);
        return ResponseEntity.ok(authorService.getAuthorById(authorId));
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable Long authorId) {
        log.info("AUTHOR ID : {}", authorId);
        authorService.deleteAuthorById(authorId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        log.info("AUTHOR : {}", authorDTO);
        return new ResponseEntity<AuthorDTO>(authorService.createAuthor(authorDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> updateAuthorById(@PathVariable Long authorId, @RequestBody Map<String, Object> fieldsToBeUpdated) {
        log.info("AUTHOR ID : {}, FIELD TO BE UPDATED : {}", authorId, fieldsToBeUpdated.toString());
        return ResponseEntity.ok(authorService.updateAuthorById(authorId, fieldsToBeUpdated));
    }
}
