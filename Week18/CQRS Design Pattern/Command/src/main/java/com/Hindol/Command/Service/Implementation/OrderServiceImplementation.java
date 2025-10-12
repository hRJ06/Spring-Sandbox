package com.Hindol.Command.Service.Implementation;

import com.Hindol.Command.DTO.OrderItemDTO;
import com.Hindol.Command.DTO.PlaceOrderCommand;
import com.Hindol.Command.Entity.Order;
import com.Hindol.Command.Event.OrderEvent;
import com.Hindol.Command.Entity.OrderItem;
import com.Hindol.Command.Exception.ResourceNotFoundException;
import com.Hindol.Command.Repository.OrderItemRepository;
import com.Hindol.Command.Repository.OrderRepository;
import com.Hindol.Command.Service.OrderEventService;
import com.Hindol.Command.Service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.Hindol.Command.Event.OrderEventType.ORDER_CANCELLED;
import static com.Hindol.Command.Event.OrderEventType.ORDER_PLACED;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImplementation implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderEventService orderEventService;

    @Override
    @Transactional
    public void placeOrder(PlaceOrderCommand command) {
        double total = command.getItems().stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        Order order = Order.builder()
                .customerId(command.getCustomerId())
                .totalAmount(total)
                .build();
        orderRepository.save(order);

        log.info("ORDER - {} saved.", order.getId());

        List<OrderItemDTO> itemDTOList = command.getItems();
        for(OrderItemDTO item : itemDTOList) {
            OrderItem orderItem = OrderItem.builder()
                    .orderId(order.getId())
                    .productId(item.getProductId())
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .build();
            orderItemRepository.save(orderItem);
            log.info("ORDER ITEM - {} saved.", orderItem.getId());
        }

        Map<String, Object> payload = Map.of(
                "customerId", command.getCustomerId(),
                "total", total,
                "items", command.getItems()
        );

        OrderEvent event = OrderEvent.builder()
                .orderId(order.getId())
                .timestamp(order.getCreatedAt().toString())
                .eventType(ORDER_PLACED)
                .payload(payload)
                .build();

        orderEventService.pushEvent(event);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("No Order exist with ID - " + orderId));
        orderRepository.delete(order);
        orderItemRepository.deleteAllByOrderId(orderId);

        OrderEvent event = OrderEvent.builder()
                .orderId(orderId)
                .timestamp(LocalDateTime.now().toString())
                .eventType(ORDER_CANCELLED)
                .build();

        log.info("ORDER - {} deleted.", orderId);

        orderEventService.pushEvent(event);
    }
}
