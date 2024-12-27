package com.Hindol.Inventory.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "Order-Service", path = "/orders")
public interface OrdersOpenFeignClient {
    @GetMapping("/core/hello")
    String helloOrders();
}
