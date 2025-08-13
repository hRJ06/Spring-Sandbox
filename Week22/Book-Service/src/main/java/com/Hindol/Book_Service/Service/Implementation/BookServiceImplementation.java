package com.Hindol.Book_Service.Service.Implementation;

import com.Hindol.Book_Service.DTO.BookDTO;
import com.Hindol.Book_Service.Entity.BookEntity;
import com.Hindol.Book_Service.Exception.ResourceNotFoundException;
import com.Hindol.Book_Service.Repository.BookRepository;
import com.Hindol.Book_Service.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImplementation implements BookService {
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        BookEntity book = modelMapper.map(bookDTO, BookEntity.class);
        BookEntity savedBook = bookRepository.save(book);
        return modelMapper.map(savedBook, BookDTO.class);
    }

    @Override
    public BookDTO getBook(Long bookId) {
        boolean exist = bookRepository.existsById(bookId);
        if(!exist) {
            throw new ResourceNotFoundException("No Book exist with ID - " + bookId);
        }
        BookEntity book = bookRepository.findById(bookId).get();
        return modelMapper.map(book, BookDTO.class);
    }
}
