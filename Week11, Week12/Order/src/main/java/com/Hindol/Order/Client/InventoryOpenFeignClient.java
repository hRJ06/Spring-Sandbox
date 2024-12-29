package com.Hindol.Order.Client;

import com.Hindol.Order.DTO.OrderRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Inventory-Service", path = "/inventory", url = "${INVENTORY_SERVICE_URI:}")
public interface InventoryOpenFeignClient {
    @PutMapping("/products/reduce-stocks")
    Double reduceStocks(@RequestBody OrderRequestDTO orderRequestDTO);
}
