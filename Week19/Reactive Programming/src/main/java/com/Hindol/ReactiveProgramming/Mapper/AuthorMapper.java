package com.Hindol.ReactiveProgramming.Mapper;


import com.Hindol.ReactiveProgramming.DTO.AuthorDTO;
import com.Hindol.ReactiveProgramming.Entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author dtoToEntity(AuthorDTO authorDTO);
    AuthorDTO entityToDto(Author author);
}
