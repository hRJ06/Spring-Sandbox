package com.Hindol.Week18.Publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routingKey.name}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        log.info("Sending message - {}", message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        log.info("Message sent - {}", message);
    }
}
