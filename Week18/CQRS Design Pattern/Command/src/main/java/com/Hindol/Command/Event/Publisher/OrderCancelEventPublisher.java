package com.Hindol.Command.Event.Publisher;

import com.Hindol.Command.Event.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("ORDER_CANCELLED")
@RequiredArgsConstructor
@Slf4j
public class OrderCancelEventPublisher implements OrderEventPublisherStrategy{
    @Value("${kafka.topic.order-cancel-topic}")
    private String KAFKA_ORDER_CANCEL_TOPIC;

    private final KafkaTemplate<Long, OrderEvent> kafkaTemplate;

    @Override
    public void publish(OrderEvent event) {
        kafkaTemplate.send(KAFKA_ORDER_CANCEL_TOPIC, event.getOrderId(), event);
        log.info("Published CANCEL event for orderId={} to topic={}", event.getOrderId(), KAFKA_ORDER_CANCEL_TOPIC);
    }
}
