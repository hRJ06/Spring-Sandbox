package com.Hindol.Command.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderCommand {
    private Long customerId;
    private List<OrderItemDTO> items;
}
