package com.Hindol.Query.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {
    private Long productId;
    private int quantity;
    private double price;
}
