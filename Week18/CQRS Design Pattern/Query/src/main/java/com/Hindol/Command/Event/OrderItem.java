package com.Hindol.Command.Event;

import lombok.Data;

@Data
public class OrderItem {
    private Long productId;
    private int quantity;
    private double price;
}
