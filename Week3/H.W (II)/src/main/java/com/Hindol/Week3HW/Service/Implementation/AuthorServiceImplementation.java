package com.Hindol.Week3HW.Service.Implementation;

import com.Hindol.Week3HW.DTO.AuthorDTO;
import com.Hindol.Week3HW.Entity.AuthorEntity;
import com.Hindol.Week3HW.Exception.ResourceNotFoundException;
import com.Hindol.Week3HW.Repository.AuthorRepository;
import com.Hindol.Week3HW.Service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthorServiceImplementation implements AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AuthorServiceImplementation(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    private void checkIfAuthorExistsById(Long authorId) {
        boolean check = authorRepository.existsById(authorId);
        if(!check) {
            log.info("No Author found with ID : {}", authorId);
            throw new ResourceNotFoundException("No Author found with ID : " + authorId);
        }
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        log.info("Getting all Authors.");
        return authorRepository.findAll().stream().map(authorEntity -> modelMapper.map(authorEntity, AuthorDTO.class)).collect(Collectors.toList());
    }

    @Override
    public AuthorDTO getAuthorById(Long authorId) {
        log.info("Getting Author By ID : {}", authorId);
        checkIfAuthorExistsById(authorId);
        AuthorEntity authorEntity = authorRepository.findById(authorId).orElse(null);
        log.info("Fetched Author by ID : {}", authorId);
        return modelMapper.map(authorEntity, AuthorDTO.class);
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        log.info("Creating Author with Name : {}", authorDTO.getName());
        AuthorEntity author = modelMapper.map(authorDTO, AuthorEntity.class);
        AuthorEntity savedAuthor = authorRepository.save(author);
        log.info("Successfully created Author with ID : {}", savedAuthor.getId());
        return modelMapper.map(savedAuthor, AuthorDTO.class);
    }

    @Override
    public AuthorDTO updateAuthorById(Long authorId, Map<String, Object> fieldsToBeChanged) {
        log.info("Updating Author with ID : {}", authorId);
        checkIfAuthorExistsById(authorId);
        AuthorEntity authorEntity = authorRepository.findById(authorId).orElse(null);
        fieldsToBeChanged.forEach((field, value) -> {
            Field fieldToBeChanged = ReflectionUtils.findRequiredField(AuthorEntity.class, field);
            fieldToBeChanged.setAccessible(true);
            ReflectionUtils.setField(fieldToBeChanged, authorEntity, value);
        });
        AuthorEntity updatedAuthor = authorRepository.save(authorEntity);
        log.info("Updated Author with ID : {}", updatedAuthor.getId());
        return modelMapper.map(updatedAuthor, AuthorDTO.class);
    }

    @Override
    public Boolean deleteAuthorById(Long authorId) {
        log.info("Deleting Author By ID : {}", authorId);
        checkIfAuthorExistsById(authorId);
        authorRepository.deleteById(authorId);
        log.info("Deleted Author By ID : {}", authorId);
        return true;
    }

    @Override
    public List<AuthorDTO> getAuthorsByName(String authorName) {
        log.info("Getting Author by Name : {}", authorName);
        String name = '%' + authorName + '%';
        List<AuthorEntity> authorEntityList = authorRepository.findByNameLike(name);
        log.info("Successfully fetched Author by Name : {}", authorName);
        return authorEntityList.stream().map((authorEntity -> modelMapper.map(authorEntity, AuthorDTO.class))).collect(Collectors.toList());
    }
}
