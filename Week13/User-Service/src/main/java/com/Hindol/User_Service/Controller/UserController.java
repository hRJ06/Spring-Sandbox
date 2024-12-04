package com.Hindol.User_Service.Controller;

import com.Hindol.User_Service.DTO.CreateUserRequestDTO;
import com.Hindol.User_Service.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Value("${kafka.topic.user-random-topic}")
    private String KAFKA_RANDOM_USER_TOPIC;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserService userService;
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        userService.createUser(createUserRequestDTO);
        return ResponseEntity.ok("User is created");
    }

    @PostMapping("/{message}")
    public ResponseEntity<String> sendMessage(@PathVariable String message) {
        for(int i = 0; i<1000; i++) {
            kafkaTemplate.send(KAFKA_RANDOM_USER_TOPIC, "" + i % 3,message + i);
        }
        return ResponseEntity.ok("Messaged queued");
    }
}
