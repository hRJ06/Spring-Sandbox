package com.Hindol.Order.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;
}
