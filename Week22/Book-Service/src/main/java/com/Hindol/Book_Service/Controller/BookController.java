package com.Hindol.Book_Service.Controller;

import com.Hindol.Book_Service.DTO.BookDTO;
import com.Hindol.Book_Service.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.addBook(bookDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<BookDTO> getBook(@RequestParam("id") Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }
}
