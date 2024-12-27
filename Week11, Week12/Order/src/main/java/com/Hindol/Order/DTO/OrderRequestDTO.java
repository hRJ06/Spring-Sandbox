package com.Hindol.Order.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequestDTO {
    private Long id;
    private List<OrderRequestItemDTO> items;
    private BigDecimal totalPrice;
}
