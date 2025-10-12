package com.Hindol.Command.Service;

import com.Hindol.Command.DTO.PlaceOrderCommand;

public interface OrderService {
    void placeOrder(PlaceOrderCommand command);
    void cancelOrder(Long orderId);
}
