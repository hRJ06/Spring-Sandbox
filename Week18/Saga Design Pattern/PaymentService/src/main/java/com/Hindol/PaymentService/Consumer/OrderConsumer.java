package com.Hindol.PaymentService.Consumer;

import com.Hindol.OrderService.Event.OrderEvent;
import com.Hindol.PaymentService.Event.PaymentEvent;
import com.Hindol.PaymentService.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderConsumer {
    @Value("${kafka.topic.payment-create-topic}")
    private String KAFKA_PAYMENT_CREATE_TOPIC;

    private final PaymentService paymentService;
    private final KafkaTemplate<Integer, PaymentEvent> kafkaTemplate;

    @KafkaListener(topics = "order-create-topic")
    public void handleOrderCreateTopic(OrderEvent orderEvent) {
        log.info("Order event received - {}", orderEvent);
        PaymentEvent paymentEvent = paymentService.newOrderEvent(orderEvent);
        kafkaTemplate.send(KAFKA_PAYMENT_CREATE_TOPIC, paymentEvent.getPaymentRequestDTO().getOrderId(), paymentEvent);
    }
}
