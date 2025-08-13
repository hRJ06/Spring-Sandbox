package com.Hindol.Library_Service.Service.Implementation;

import com.Hindol.Library_Service.DTO.BookDTO;
import com.Hindol.Library_Service.DTO.UserDTO;
import com.Hindol.Library_Service.Entity.UserEntity;
import com.Hindol.Library_Service.Exception.ResourceNotFoundException;
import com.Hindol.Library_Service.Repository.UserRepository;
import com.Hindol.Library_Service.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    @Value("${bookService.baseUrl}")
    private String BASE_URL;

    private BookDTO fetchBook(Long bookId) {
        String url = UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/get")
                .queryParam("id", bookId)
                .toUriString();
        try {
            ResponseEntity<BookDTO> response = restTemplate.getForEntity(url, BookDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("No Book exist with ID - " + bookId);
        }
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        UserEntity user = modelMapper.map(userDTO, UserEntity.class);
        UserEntity savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO rentBook(Long userId, Long bookId) {
        BookDTO bookDTO = fetchBook(bookId);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found."));

        user.getRentedBook().add(bookDTO.getId());
        UserEntity savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO getProfile(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found."));

        List<BookDTO> bookList = user.getRentedBook().stream()
                .map(this::fetchBook)
                .filter(Objects::nonNull)
                .toList();

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setBook(bookList);
        return userDTO;
    }
}
