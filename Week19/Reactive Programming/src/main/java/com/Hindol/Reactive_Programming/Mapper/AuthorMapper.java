package com.Hindol.Reactive_Programming.Mapper;


import com.Hindol.Reactive_Programming.DTO.AuthorDTO;
import com.Hindol.Reactive_Programming.Entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author dtoToEntity(AuthorDTO authorDTO);
    AuthorDTO entityToDto(Author author);
}
