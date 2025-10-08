package com.Hindol.Command.Configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {
    @Value("${kafka.topic.order-topic}")
    private String KAFKA_ORDER_TOPIC;

    @Bean
    public NewTopic orderTopic() {
        return new NewTopic(KAFKA_ORDER_TOPIC, 3, (short)1);
    }
}
