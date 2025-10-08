package com.Hindol.Command.Service.Implementation;

import com.Hindol.Command.Entity.OrderEvent;
import com.Hindol.Command.Repository.OrderEventRepository;
import com.Hindol.Command.Service.OrderEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventServiceImplementation implements OrderEventService {
    @Value("${kafka.topic.order-topic}")
    private String KAFKA_ORDER_TOPIC;

    private final OrderEventRepository orderEventRepository;
    private final KafkaTemplate<Long, OrderEvent> kafkaTemplate;

    @Override
    @Async
    public void pushEvent(OrderEvent orderEvent) {
        log.info("Saving Order Event in DynamoDB.");
        orderEventRepository.addEvent(orderEvent);
        log.info("Order Event saved.");

        log.info("Publishing Kafka Event");
        kafkaTemplate.send(KAFKA_ORDER_TOPIC, orderEvent.getOrderId(), orderEvent);
        log.info("Kafka Event published.");
    }
}
