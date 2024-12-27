package com.Hindol.Inventory.DTO;

import lombok.Data;

@Data
public class OrderRequestItemDTO {
    private Long productId;
    private Integer quantity;
}
