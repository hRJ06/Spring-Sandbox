package com.Hindol.User_Service.Configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {
    @Value("${kafka.topic.user-random-topic}")
    private String KAFKA_RANDOM_USER_TOPIC;
    @Bean
    public NewTopic userRandomTopic() {
        return new NewTopic(KAFKA_RANDOM_USER_TOPIC, 3, (short) 1);
    }
}
