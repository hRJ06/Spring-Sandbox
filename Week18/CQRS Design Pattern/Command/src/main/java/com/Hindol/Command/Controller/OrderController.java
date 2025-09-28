package com.Hindol.Command.Controller;

import com.Hindol.Command.DTO.PlaceOrderCommand;
import com.Hindol.Command.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderCommand request) {
        orderService.placeOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
