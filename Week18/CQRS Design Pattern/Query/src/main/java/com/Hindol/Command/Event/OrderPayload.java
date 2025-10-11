package com.Hindol.Command.Event;

import lombok.Data;

import java.util.List;

@Data
public class OrderPayload {
    private Long customerId;
    private Double total;
    private List<OrderItem> items;
}
