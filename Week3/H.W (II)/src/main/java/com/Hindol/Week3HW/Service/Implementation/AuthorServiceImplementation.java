package com.Hindol.Week3HW.Service.Implementation;

import com.Hindol.Week3HW.DTO.AuthorDTO;
import com.Hindol.Week3HW.Entity.AuthorEntity;
import com.Hindol.Week3HW.Exception.ResourceNotFoundException;
import com.Hindol.Week3HW.Repository.AuthorRepository;
import com.Hindol.Week3HW.Repository.BookRepository;
import com.Hindol.Week3HW.Service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImplementation implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    private void checkIfAuthorExistsById(Long authorId) {
        boolean check = authorRepository.existsById(authorId);
        if(!check) throw new ResourceNotFoundException("No Author found with ID : " + authorId);
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream().map(authorEntity -> modelMapper.map(authorEntity, AuthorDTO.class)).collect(Collectors.toList());
    }

    @Override
    public AuthorDTO getAuthorById(Long authorId) {
        checkIfAuthorExistsById(authorId);
        AuthorEntity authorEntity = authorRepository.findById(authorId).orElse(null);
        return modelMapper.map(authorEntity, AuthorDTO.class);
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        AuthorEntity author = modelMapper.map(authorDTO, AuthorEntity.class);
        return modelMapper.map(authorRepository.save(author), AuthorDTO.class);
    }

    @Override
    public AuthorDTO updateAuthorById(Long authorId, Map<String, Object> fieldsToBeChanged) {
        checkIfAuthorExistsById(authorId);
        AuthorEntity authorEntity = authorRepository.findById(authorId).orElse(null);
        fieldsToBeChanged.forEach((field, value) -> {
            Field fieldToBeChanged = ReflectionUtils.findRequiredField(AuthorEntity.class, field);
            fieldToBeChanged.setAccessible(true);
            ReflectionUtils.setField(fieldToBeChanged, authorEntity, value);
        });
        return modelMapper.map(authorRepository.save(authorEntity),AuthorDTO.class);
    }

    @Override
    public Boolean deleteAuthorById(Long authorId) {
        checkIfAuthorExistsById(authorId);
        authorRepository.deleteById(authorId);
        return true;
    }

    @Override
    public List<AuthorDTO> getAuthorsByName(String authorName) {
        String name = '%' + authorName + '%';
        return authorRepository.findByNameLike(name).stream().map((authorEntity -> modelMapper.map(authorEntity, AuthorDTO.class))).collect(Collectors.toList());
    }
}
