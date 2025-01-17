package com.Hindol.PaymentService.Configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {
    @Value("${kafka.topic.payment-create-topic}")
    private String KAFKA_PAYMENT_CREATE_TOPIC;
    @Bean
    public NewTopic createOrderTopic() {
        return new NewTopic(KAFKA_PAYMENT_CREATE_TOPIC, 3, (short) 1);
    }
}
