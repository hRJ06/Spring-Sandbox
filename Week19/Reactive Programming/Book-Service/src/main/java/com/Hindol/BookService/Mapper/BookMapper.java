package com.Hindol.BookService.Mapper;

import com.Hindol.BookService.DTO.BookDTO;
import com.Hindol.BookService.Entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book dtoToEntity(BookDTO bookDTO);
    BookDTO entityToDto(Book book);
}
