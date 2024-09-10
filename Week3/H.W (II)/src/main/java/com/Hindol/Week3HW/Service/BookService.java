package com.Hindol.Week3HW.Service;

import com.Hindol.Week3HW.DTO.BookDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookService {
    public BookDTO getBookById(Long id);
    public List<BookDTO> getAllBooks();
    public BookDTO createBook(BookDTO bookDTO);
    public BookDTO updatedBookById(Long id, Map<String, Object> fieldsToBeUpdated);
    public void deleteBookById(Long id);
    public List<BookDTO> getBooksPublishedAfterDate(LocalDate date);
    public List<BookDTO> getBooksByTitle(String title);
    public List<BookDTO> getBooksByAuthor(Long authorId);
    public BookDTO assignAuthorToBook(Long bookId, Long authorId);
}
