package com.Hindol.Week5.Service.Implementation;

import com.Hindol.Week5.DTO.SignUpDTO;
import com.Hindol.Week5.DTO.UserDTO;
import com.Hindol.Week5.Entity.UserEntity;
import com.Hindol.Week5.Exception.ResourceNotFoundException;
import com.Hindol.Week5.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("No User found with Email " + email));
    }
    public UserDTO signUp(SignUpDTO signUpDTO) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(signUpDTO.getEmail());
        if(userEntity.isPresent()) {
            throw new BadCredentialsException("User with Email " + signUpDTO.getEmail() + " already exists");
        }
        UserEntity toBeCreatedUser = modelMapper.map(signUpDTO, UserEntity.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        UserEntity savedUser = userRepository.save(toBeCreatedUser);
        return modelMapper.map(savedUser, UserDTO.class);
    }
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No User found with ID " + userId));
    }
}
