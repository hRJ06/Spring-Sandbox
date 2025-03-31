package com.Hindol.Reactive_Programming.Mapper;

import com.Hindol.Reactive_Programming.DTO.BookDTO;
import com.Hindol.Reactive_Programming.Entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book dtoToEntity(BookDTO bookDTO);
    BookDTO entityToDto(Book book);
}
