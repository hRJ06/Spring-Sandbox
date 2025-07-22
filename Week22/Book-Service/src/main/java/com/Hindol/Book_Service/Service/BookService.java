package com.Hindol.Book_Service.Service;

import com.Hindol.Book_Service.DTO.BookDTO;

public interface BookService {
    BookDTO addBook(BookDTO bookDTO);
    BookDTO getBook(Long bookId);
}
