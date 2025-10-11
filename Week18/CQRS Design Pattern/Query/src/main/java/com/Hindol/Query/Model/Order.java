package com.Hindol.Query.Model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "order")
public class Order {
    @Id
    private String id;

    private Long orderId;
    private Long customerId;
    private Double totalAmount;
    private LocalDateTime createdAt;

    private List<OrderItem> items;

}

