package com.Hindol.Command.Configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {
    @Value("${kafka.topic.order-create-topic}")
    private String KAFKA_ORDER_CREATE_TOPIC;

    @Value("${kafka.topic.order-cancel-topic}")
    private String KAFKA_ORDER_CANCEL_TOPIC;

    @Bean
    public NewTopic orderTopic() {
        return new NewTopic(KAFKA_ORDER_CREATE_TOPIC, 3, (short)1);
    }
}
