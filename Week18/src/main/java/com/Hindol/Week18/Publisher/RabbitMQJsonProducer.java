package com.Hindol.Week18.Publisher;

import com.Hindol.Week18.DTO.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQJsonProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.jsonRoutingKey.name}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(User user) {
        log.info("Sending message - {}", user.toString());
        rabbitTemplate.convertAndSend(exchange, routingKey, user);
        log.info("Message sent - {}", user);
    }
}
