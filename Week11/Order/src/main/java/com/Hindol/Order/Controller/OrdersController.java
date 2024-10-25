package com.Hindol.Order.Controller;

import com.Hindol.Order.DTO.OrderRequestDTO;
import com.Hindol.Order.Order.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;
    @GetMapping
    public ResponseEntity<List<OrderRequestDTO>> getAllOrders() {
        log.info("Fetching all orders via controller");
        List<OrderRequestDTO> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDTO> getOrderById(@PathVariable Long id) {
        log.info("Fetching order with ID : {} via controller", id);
        OrderRequestDTO order = ordersService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
}
