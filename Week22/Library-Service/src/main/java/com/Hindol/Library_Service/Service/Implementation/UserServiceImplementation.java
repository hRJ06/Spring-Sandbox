package com.Hindol.Library_Service.Service.Implementation;

import com.Hindol.Library_Service.DTO.BookDTO;
import com.Hindol.Library_Service.DTO.UserDTO;
import com.Hindol.Library_Service.Entity.UserEntity;
import com.Hindol.Library_Service.Repository.UserRepository;
import com.Hindol.Library_Service.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    @Value("${bookService.baseUrl}")
    private String BASE_URL;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        UserEntity user = modelMapper.map(userDTO, UserEntity.class);
        UserEntity savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public void rentBook(Long userId, Long bookId) {
        String url = BASE_URL + "/get?id=" + bookId;
        BookDTO bookDTO = restTemplate.getForObject(url, BookDTO.class);
        UserEntity user = userRepository.findById(userId).get();
        if(Objects.nonNull(bookDTO)) {
            user.getRentedBook().add(bookDTO.getId());
            userRepository.save(user);
        }
    }
}
