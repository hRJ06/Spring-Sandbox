package com.Hindol.Query.Consumer;

import com.Hindol.Command.Event.OrderEvent;
import com.Hindol.Command.Event.OrderPayload;
import com.Hindol.Query.Exception.ResourceNotFoundException;
import com.Hindol.Query.Model.Order;
import com.Hindol.Query.Model.OrderItem;
import com.Hindol.Query.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderConsumer {
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "order_create_topic")
    public void handleOrder(OrderEvent event) {
        try {
            log.info("Received Order Create Event.");
            OrderPayload payload = event.getPayload();
            List<OrderItem> items = payload.getItems().stream()
                    .map(i -> OrderItem.builder()
                            .productId(i.getProductId())
                            .quantity(i.getQuantity())
                            .price(i.getPrice())
                            .build()
                    ).toList();

            Order order = Order.builder()
                    .orderId(event.getOrderId())
                    .customerId(payload.getCustomerId())
                    .totalAmount(payload.getTotal())
                    .createdAt(LocalDateTime.parse(event.getTimestamp()))
                    .items(items)
                    .build();
            orderRepository.save(order);
            log.info("Order saved for Id - {}", event.getOrderId());
        }
        catch (Exception e) {
            log.error("Failed to process Order Create Event - ", e);
        }
    }

    @KafkaListener(topics = "order_cancel_topic")
    public void cancelOrder(OrderEvent event) {
        try {
            log.info("Received Order Cancel Event.");
            Long orderId = event.getOrderId();
            Order order = orderRepository.findByOrderId(orderId).orElseThrow(() -> new ResourceNotFoundException("No Order exist with ID - " + orderId));
            orderRepository.delete(order);
            log.info("Order - {}deleted.", orderId);
        }
        catch (Exception e) {
            log.error("Failed to process Order Cancel Event - ", e);
        }
    }
}
