package com.Hindol.Query.Service;

import com.Hindol.Query.DTO.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> findByCustomerId(Long customerId);
    OrderDTO findById(Long orderId);
}
