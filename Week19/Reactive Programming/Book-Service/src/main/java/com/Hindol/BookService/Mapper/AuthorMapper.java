package com.Hindol.BookService.Mapper;


import com.Hindol.BookService.DTO.AuthorDTO;
import com.Hindol.BookService.Entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author dtoToEntity(AuthorDTO authorDTO);
    AuthorDTO entityToDto(Author author);
}
