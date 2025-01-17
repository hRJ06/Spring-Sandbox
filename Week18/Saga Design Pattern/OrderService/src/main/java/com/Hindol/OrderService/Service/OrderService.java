package com.Hindol.OrderService.Service;

import com.Hindol.OrderService.Enum.OrderStatus;
import com.Hindol.OrderService.Event.OrderEvent;
import com.Hindol.OrderService.DTO.OrderRequestDTO;
import com.Hindol.OrderService.Entity.PurchaseOrder;
import com.Hindol.OrderService.Repository.OrderRepository;
import com.Hindol.PaymentService.Enum.PaymentStatus;
import com.Hindol.PaymentService.Event.PaymentEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Value("${kafka.topic.order-create-topic}")
    private String KAFKA_ORDER_CREATE_TOPIC;

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Integer, OrderEvent> kafkaTemplate;

    @Transactional
    public PurchaseOrder createOrder(OrderRequestDTO orderRequestDTO) {
        PurchaseOrder purchaseOrder = modelMapper.map(orderRequestDTO, PurchaseOrder.class);
        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
        PurchaseOrder savedOrder = orderRepository.save(purchaseOrder);
        orderRequestDTO.setOrderId(savedOrder.getId());

        OrderEvent orderEvent = new OrderEvent(orderRequestDTO, OrderStatus.ORDER_CREATED);
        kafkaTemplate.send(KAFKA_ORDER_CREATE_TOPIC, savedOrder.getId(), orderEvent);

        return savedOrder;
    }

    @Transactional
    public void updateOrder(PaymentEvent paymentEvent) {
        Integer orderId = paymentEvent.getPaymentRequestDTO().getOrderId();
        PurchaseOrder purchaseOrder = orderRepository.findById(orderId).orElse(null);
        if(purchaseOrder != null) {
            boolean isComplete = PaymentStatus.PAYMENT_COMPLETED.equals(paymentEvent.getPaymentStatus());
            OrderStatus orderStatus = isComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
            purchaseOrder.setOrderStatus(orderStatus);
            purchaseOrder.setPaymentStatus(paymentEvent.getPaymentStatus());
            orderRepository.save(purchaseOrder);
        }
    }
}
