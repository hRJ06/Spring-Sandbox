package com.Hindol.Command.DTO;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long productId;
    private int quantity;
    private double price;
}
