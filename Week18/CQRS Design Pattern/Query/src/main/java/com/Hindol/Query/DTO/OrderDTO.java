package com.Hindol.Query.DTO;

import com.Hindol.Query.Model.OrderItem;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long orderId;
    private Long customerId;
    private Double totalAmount;
    private LocalDateTime createdAt;
    private List<OrderItem> items;

}
