package com.Hindol.Command.Service.Implementation;

import com.Hindol.Command.DTO.OrderItemDTO;
import com.Hindol.Command.DTO.PlaceOrderCommand;
import com.Hindol.Command.Entity.OrderEntity;
import com.Hindol.Command.Entity.OrderItemEntity;
import com.Hindol.Command.Repository.OrderItemRepository;
import com.Hindol.Command.Repository.OrderRepository;
import com.Hindol.Command.Service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementation implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public void placeOrder(PlaceOrderCommand command) {
        OrderEntity order = OrderEntity.builder()
                .customerId(command.getCustomerId())
                .totalAmount(command.getItems().stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum())
                .build();
        orderRepository.save(order);

        List<OrderItemDTO> itemDTOList = command.getItems();
        for(OrderItemDTO item : itemDTOList) {
            OrderItemEntity orderItem = OrderItemEntity.builder()
                    .orderId(order.getId())
                    .productId(item.getProductId())
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .build();
            orderItemRepository.save(orderItem);
        }

    }
}
