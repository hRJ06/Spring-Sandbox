package com.Hindol.OrderService.Consumer;

import com.Hindol.OrderService.Service.OrderService;
import com.Hindol.PaymentService.Event.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "payment-create-topic")
    public void handlePaymentCreateTopic(PaymentEvent paymentEvent) {
        log.info("Payment event received - {}", paymentEvent);
        orderService.updateOrder(paymentEvent);
    }
}
