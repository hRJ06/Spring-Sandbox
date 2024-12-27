package com.Hindol.Inventory.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private List<OrderRequestItemDTO> items;
}
