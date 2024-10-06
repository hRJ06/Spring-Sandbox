package com.Hindol.AOP.Service;

public interface ShipmentService {
    String orderPackage(Long orderId);

    String trackPackage(Long orderId);
}
