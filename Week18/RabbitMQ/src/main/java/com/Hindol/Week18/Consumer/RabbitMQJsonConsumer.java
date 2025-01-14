package com.Hindol.Week18.Consumer;

import com.Hindol.Week18.DTO.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQJsonConsumer {
    @RabbitListener(queues = {"${rabbitmq.jsonQueue.name}"})
    public void consume(User user) {
        log.info("Received message -> {}", user.toString());
    }
}
