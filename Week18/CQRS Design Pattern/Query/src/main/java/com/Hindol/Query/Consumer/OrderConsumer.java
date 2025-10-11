package com.Hindol.Query.Consumer;

import com.Hindol.Command.Event.OrderEvent;
import com.Hindol.Command.Event.OrderPayload;
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

    @KafkaListener(topics = "order_topic")
    public void handleOrder(OrderEvent event) {
        try {
            log.info("Received Order Event.");
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
            log.error("Failed to process Order Event - ", e);
        }

    }
}
