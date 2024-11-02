package com.Hindol.Order.Controller;

import com.Hindol.Order.DTO.OrderRequestDTO;
import com.Hindol.Order.Order.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;

    @GetMapping("/hello")
    public String helloOrder() {
        return "Hello from Order";
    }
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

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        OrderRequestDTO orderRequestDTO1 = ordersService.createOrder(orderRequestDTO);
        return ResponseEntity.ok(orderRequestDTO1);
    }
}
