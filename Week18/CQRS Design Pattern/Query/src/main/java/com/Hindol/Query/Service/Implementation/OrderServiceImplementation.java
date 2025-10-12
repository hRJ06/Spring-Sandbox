package com.Hindol.Query.Service.Implementation;

import com.Hindol.Query.DTO.OrderDTO;
import com.Hindol.Query.Exception.ResourceNotFoundException;
import com.Hindol.Query.Model.Order;
import com.Hindol.Query.Repository.OrderRepository;
import com.Hindol.Query.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementation implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper mapper;

    @Override
    public OrderDTO findById(Long orderId) {
        Optional<Order> order = orderRepository.findByOrderId(orderId);
        if(order.isPresent()) {
            return mapper.map(order, OrderDTO.class);
        }
        throw new ResourceNotFoundException("No Order exist with ID - " + orderId);
    }

    @Override
    public List<OrderDTO> findByCustomerId(Long customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        return orders.stream().map(order -> mapper.map(order, OrderDTO.class)).collect(Collectors.toList());
    }
}
