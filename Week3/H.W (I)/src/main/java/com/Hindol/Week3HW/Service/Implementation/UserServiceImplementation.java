package com.Hindol.Week3HW.Service.Implementation;

import com.Hindol.Week3HW.DTO.SignUpDTO;
import com.Hindol.Week3HW.DTO.UserDTO;
import com.Hindol.Week3HW.Entity.UserEntity;
import com.Hindol.Week3HW.Exception.ResourceNotFoundException;
import com.Hindol.Week3HW.Repository.UserRepository;
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
        return userRepository.findByEmail(email).orElseThrow(() -> new BadCredentialsException("No User found with email " + email));
    }
    public UserDTO signUp(SignUpDTO signUpDTO) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(signUpDTO.getEmail());
        if(userEntity.isPresent()) {
            throw new BadCredentialsException("User with email " + signUpDTO.getEmail() + " already exists");
        }
        UserEntity userToBeCreated = modelMapper.map(signUpDTO, UserEntity.class);
        userToBeCreated.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        UserEntity savedUser = userRepository.save(userToBeCreated);
        return modelMapper.map(savedUser, UserDTO.class);
    }
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No User found with ID " + id));
    }
}
