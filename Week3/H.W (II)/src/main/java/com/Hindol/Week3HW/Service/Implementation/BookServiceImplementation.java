package com.Hindol.Week3HW.Service.Implementation;

import com.Hindol.Week3HW.DTO.BookDTO;
import com.Hindol.Week3HW.Entity.AuthorEntity;
import com.Hindol.Week3HW.Entity.BookEntity;
import com.Hindol.Week3HW.Exception.ResourceNotFoundException;
import com.Hindol.Week3HW.Repository.AuthorRepository;
import com.Hindol.Week3HW.Repository.BookRepository;
import com.Hindol.Week3HW.Service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        log.info("Fetching Book by ID : {}", id);
        checkIfBookExistById(id);
        BookEntity book = this.bookRepository.findById(id).orElse(null);
        log.info("Successfully fetched Book by ID : {}", id);
        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        log.info("Fetching Books");
        List<BookEntity> bookEntities = this.bookRepository.findAll();
        log.info("Successfully fetched all Books");
        return bookEntities.stream().map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class)).collect(Collectors.toList());
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        log.info("Creating Book");
        BookEntity book = modelMapper.map(bookDTO, BookEntity.class);
        BookEntity createdBook = bookRepository.save(book);
        log.info("Created Book");
        return modelMapper.map(createdBook, BookDTO.class);
    }

    @Override
    public BookDTO updatedBookById(Long id, Map<String, Object> fieldsToBeUpdated) {
        log.info("Fetching Book with ID : {}", id);
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
        BookEntity updatedBook = bookRepository.save(book);
        log.info("Successfully updated Book with ID : {}", id);
        return modelMapper.map(updatedBook, BookDTO.class);
    }

    @Override
    public void deleteBookById(Long id) {
        log.info("Deleting Book by ID : {}", id);
        checkIfBookExistById(id);
        bookRepository.deleteById(id);
        log.info("Successfully deleted Book by ID : {}", id);
    }

    @Override
    public List<BookDTO> getBooksPublishedAfterDate(LocalDate date) {
        log.info("Fetching Books with Published Date after : {}", date);
        List<BookEntity> bookEntities = bookRepository.findByPublishDateAfter(date);
        log.info("Successfully fetched Books with Published Date after : {}", date);
        return bookEntities.stream().map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBooksByTitle(String title) {
        log.info("Fetching Books with Title : {}", title);
        String updatedTitle = '%' + title + '%';
        List<BookEntity> bookEntities = bookRepository.findByTitleLike(updatedTitle);
        log.info("Successfully fetched Books with Title : {}", title);
        return bookEntities.stream().map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBooksByAuthor(Long authorId) {
        log.info("Fetching Author by ID : {}", authorId);
        AuthorEntity author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("No Author found with ID : " + authorId));
        log.info("Successfully fetched Author by ID : {}", authorId);
        List<BookEntity> bookEntities = author.getBooks();
        log.info("Successfully fetched Books of Author having ID : {}", authorId);
        return bookEntities.stream().map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class)).collect(Collectors.toList());
    }

    @Override
    public BookDTO assignAuthorToBook(Long bookId, Long authorId) {
        log.info("Fetching Book by ID : {}, Author by ID : {}", bookId, authorId);
        checkIfBookExistById(bookId);
        AuthorEntity authorEntity = this.authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found with ID : " + authorId));
        BookEntity bookEntity = this.bookRepository.findById(bookId).orElse(null);
        log.info("Successfully fetched Book by ID : {}, Author by ID : {}", bookId, authorId);
        bookEntity.setAuthor(authorEntity);
        BookEntity updatedBook = bookRepository.save(bookEntity);
        log.info("Successfully assigned Author with ID : {} to Book with ID : {}", authorId, bookId);
        return modelMapper.map(updatedBook,BookDTO.class);
    }
}
