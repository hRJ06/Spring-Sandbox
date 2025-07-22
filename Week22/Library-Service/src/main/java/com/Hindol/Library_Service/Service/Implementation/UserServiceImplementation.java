package com.Hindol.Library_Service.Service.Implementation;

import com.Hindol.Library_Service.DTO.UserDTO;
import com.Hindol.Library_Service.Entity.UserEntity;
import com.Hindol.Library_Service.Repository.UserRepository;
import com.Hindol.Library_Service.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        UserEntity user = modelMapper.map(userDTO, UserEntity.class);
        UserEntity savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }
}
