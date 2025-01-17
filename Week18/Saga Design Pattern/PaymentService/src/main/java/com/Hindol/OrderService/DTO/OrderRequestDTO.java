package com.Hindol.OrderService.DTO;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private Integer userId;
    private Integer productId;
    private Integer amount;
    private Integer orderId;
}
