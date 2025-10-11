package com.Hindol.Command.Event;

import lombok.Data;

import java.util.Map;

@Data
public class OrderEvent {
    private Long orderId;
    private String timestamp;
    private OrderEventType eventType;
    private OrderPayload payload;
}

enum OrderEventType {
    ORDER_PLACED
}