package com.Hindol.Week3HW.Service;

import com.Hindol.Week3HW.DTO.AuthorDTO;

import java.util.List;
import java.util.Map;

public interface AuthorService {
    public List<AuthorDTO> getAllAuthors();
    public AuthorDTO getAuthorById(Long authorId);
    public AuthorDTO createAuthor(AuthorDTO authorDTO);
    public AuthorDTO updateAuthorById(Long authorId, Map<String, Object> fieldsToBeChanged);
    public void deleteAuthorById(Long authorId);
    public List<AuthorDTO> getAuthorsByName(String authorName);
}
