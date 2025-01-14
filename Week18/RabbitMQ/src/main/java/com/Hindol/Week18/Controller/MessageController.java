package com.Hindol.Week18.Controller;

import com.Hindol.Week18.DTO.User;
import com.Hindol.Week18.Publisher.RabbitMQJsonProducer;
import com.Hindol.Week18.Publisher.RabbitMQProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MessageController {
    private final RabbitMQProducer rabbitMQProducer;
    private final RabbitMQJsonProducer rabbitMQJsonProducer;

    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {
        rabbitMQProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to RabbitMQ");
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendJsonMessage(@RequestBody User user) {
        rabbitMQJsonProducer.sendMessage(user);
        return ResponseEntity.ok("Message sent to RabbitMQ");
    }
}
