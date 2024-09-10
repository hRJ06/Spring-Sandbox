package com.Hindol.Week3HW.Controller;

import com.Hindol.Week3HW.DTO.BookDTO;
import com.Hindol.Week3HW.Service.BookService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    Logger log = LoggerFactory.getLogger(BookController.class);

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBookByUId(@PathVariable Long bookId) {
        log.info("BOOK ID : {}", bookId);
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody @Valid BookDTO bookDTO) {
        log.info("BOOK : {}", bookDTO);
        return new ResponseEntity<BookDTO>(bookService.createBook(bookDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<BookDTO> updateBookById(@PathVariable Long authorId, @RequestBody Map<String, Object> fieldsTobeUpdated) {
        log.info("AUTHOR ID : {}, FIELD TO BE UPDATED : {}", authorId, fieldsTobeUpdated);
        return ResponseEntity.ok(bookService.updatedBookById(authorId, fieldsTobeUpdated));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Boolean> deleteBookById(@PathVariable Long bookId) {
        log.info("BOOK ID : {}", bookId);
        bookService.deleteBookById(bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAfterDate/{date}")
    public ResponseEntity<List<BookDTO>> getBooksPublishedAfterDate(@PathVariable LocalDate date) {
        log.info("DATE : {}", date);
        return ResponseEntity.ok(bookService.getBooksPublishedAfterDate(date));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookDTO>> getBooksByTitle(@PathVariable String title) {
        log.info("TITLE : {}", title);
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable Long authorId) {
        log.info("AUTHOR ID : {}", authorId);
        return ResponseEntity.ok(bookService.getBooksByAuthor(authorId));
    }

    @GetMapping("{bookId}/author/{authorId}")
    public ResponseEntity<BookDTO> assignBookToAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
        log.info("BOOK ID : {}, AUTHOR ID : {}", bookId, authorId);
        return ResponseEntity.ok(bookService.assignAuthorToBook(bookId, authorId));
    }

}
