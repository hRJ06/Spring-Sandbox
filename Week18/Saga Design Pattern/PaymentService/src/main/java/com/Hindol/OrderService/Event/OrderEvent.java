package com.Hindol.OrderService.Event;

import com.Hindol.OrderService.DTO.OrderRequestDTO;
import com.Hindol.OrderService.Enum.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderEvent {
    private UUID eventId = UUID.randomUUID();
    private Date date = new Date();
    private OrderRequestDTO orderRequestDTO;
    private OrderStatus orderStatus;

    public OrderEvent(OrderRequestDTO orderRequestDTO, OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        this.orderRequestDTO = orderRequestDTO;
    }
}