package com.Hindol.Week3HW.Controller;

import com.Hindol.Week3HW.DTO.LoginDTO;
import com.Hindol.Week3HW.DTO.SignUpDTO;
import com.Hindol.Week3HW.DTO.UserDTO;
import com.Hindol.Week3HW.Service.Implementation.AuthServiceImplementation;
import com.Hindol.Week3HW.Service.Implementation.UserServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImplementation authServiceImplementation;
    private final UserServiceImplementation userServiceImplementation;

    Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        log.info("SIGN UP : {}", signUpDTO);
        UserDTO userDTO = userServiceImplementation.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        log.info("LOG IN : {}", loginDTO);
        String token = authServiceImplementation.generateToken(loginDTO);
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}
