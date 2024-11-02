package com.Hindol.Order.Order;

import com.Hindol.Order.Client.InventoryOpenFeignClient;
import com.Hindol.Order.DTO.OrderRequestDTO;
import com.Hindol.Order.Entity.Enum.OrderStatus;
import com.Hindol.Order.Entity.OrderItem;
import com.Hindol.Order.Entity.Orders;
import com.Hindol.Order.Repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;

    public List<OrderRequestDTO> getAllOrders() {
        log.info("Fetching all orders");
        List<Orders> orders = ordersRepository.findAll();
        return orders.stream().map((element) -> modelMapper.map(element, OrderRequestDTO.class)).toList();
    }

    public OrderRequestDTO getOrderById(Long id) {
        log.info("Fetching order with ID : {}", id);
        Orders order = ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return modelMapper.map(order, OrderRequestDTO.class);
    }

    public OrderRequestDTO createOrder(OrderRequestDTO orderRequestDTO) {
        Double totalPrice = inventoryOpenFeignClient.reduceStocks(orderRequestDTO);
        Orders orders = modelMapper.map(orderRequestDTO, Orders.class);
        for(OrderItem orderItem: orders.getItems()) {
            orderItem.setOrder(orders);
        }
        orders.setTotalPrice(totalPrice);
        orders.setOrderStatus(OrderStatus.CONFIRMED);
        Orders savedOrder = ordersRepository.save(orders);
        return modelMapper.map(savedOrder, OrderRequestDTO.class);
    }
}
