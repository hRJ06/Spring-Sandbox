package com.Hindol.Week3HW.Service.Implementation;

import com.Hindol.Week3HW.DTO.BookDTO;
import com.Hindol.Week3HW.Entity.AuthorEntity;
import com.Hindol.Week3HW.Entity.BookEntity;
import com.Hindol.Week3HW.Exception.ResourceNotFoundException;
import com.Hindol.Week3HW.Repository.AuthorRepository;
import com.Hindol.Week3HW.Repository.BookRepository;
import com.Hindol.Week3HW.Service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookServiceImplementation implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public BookServiceImplementation(BookRepository bookRepository, AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    private void checkIfBookExistById(Long id) {
        boolean check = bookRepository.existsById(id);
        if(!check) throw new ResourceNotFoundException("No Book found with ID : " + id);
    }

    @Override
    public BookDTO getBookById(Long id) {
        checkIfBookExistById(id);
        BookEntity book = this.bookRepository.findById(id).orElse(null);
        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<BookEntity> bookEntities = this.bookRepository.findAll();
        return bookEntities.stream().map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class)).collect(Collectors.toList());
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        BookEntity book = modelMapper.map(bookDTO, BookEntity.class);
        return modelMapper.map(bookRepository.save(book), BookDTO.class);
    }

    @Override
    public BookDTO updatedBookById(Long id, Map<String, Object> fieldsToBeUpdated) {
        checkIfBookExistById(id);
        BookEntity book = this.bookRepository.findById(id).orElse(null);
        fieldsToBeUpdated.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(BookEntity.class, field);
            if(fieldToBeUpdated.getType().equals(LocalDate.class) && value instanceof String) {
                value = LocalDate.parse((String) value);
            }
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, book, value);
        });
        return modelMapper.map(bookRepository.save(book), BookDTO.class);
    }

    @Override
    public void deleteBookById(Long id) {
        checkIfBookExistById(id);
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDTO> getBooksPublishedAfterDate(LocalDate date) {
        List<BookEntity> bookEntities = bookRepository.findByPublishDateAfter(date);
        return bookEntities.stream().map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBooksByTitle(String title) {
        String updatedTitle = '%' + title + '%';
        List<BookEntity> bookEntities = bookRepository.findByTitleLike(updatedTitle);
        return bookEntities.stream().map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBooksByAuthor(Long authorId) {
        AuthorEntity author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("No Author found with ID : " + authorId));
        List<BookEntity> bookEntities = author.getBooks();
        return bookEntities.stream().map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class)).collect(Collectors.toList());
    }

    @Override
    public BookDTO assignAuthorToBook(Long bookId, Long authorId) {
        checkIfBookExistById(bookId);
        AuthorEntity authorEntity = this.authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found with ID : " + authorId));
        BookEntity bookEntity = this.bookRepository.findById(bookId).orElse(null);
        bookEntity.setAuthor(authorEntity);
        return modelMapper.map(bookRepository.save(bookEntity),BookDTO.class);
    }
}
