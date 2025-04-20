package com.Hindol.ReactiveProgramming.Mapper;

import com.Hindol.ReactiveProgramming.DTO.BookDTO;
import com.Hindol.ReactiveProgramming.Entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book dtoToEntity(BookDTO bookDTO);
    BookDTO entityToDto(Book book);
}
