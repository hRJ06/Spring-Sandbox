package com.Hindol.PaymentService.Service;


import com.Hindol.OrderService.DTO.OrderRequestDTO;
import com.Hindol.OrderService.Event.OrderEvent;
import com.Hindol.PaymentService.DTO.PaymentRequestDTO;
import com.Hindol.PaymentService.Entity.UserTransaction;
import com.Hindol.PaymentService.Enum.PaymentStatus;
import com.Hindol.PaymentService.Event.PaymentEvent;
import com.Hindol.PaymentService.Repository.UserBalanceRepository;
import com.Hindol.PaymentService.Repository.UserTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final UserBalanceRepository userBalanceRepository;
    private final UserTransactionRepository userTransactionRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDTO orderRequestDTO = orderEvent.getOrderRequestDTO();
        PaymentRequestDTO paymentRequestDTO = modelMapper.map(orderRequestDTO, PaymentRequestDTO.class);
        return userBalanceRepository.findById(orderRequestDTO.getUserId())
                .filter(userBalance -> userBalance.getBalance() > orderRequestDTO.getAmount())
                .map(userBalance -> {
                    userBalance.setBalance(userBalance.getBalance() - orderRequestDTO.getAmount());
                    userTransactionRepository.save(modelMapper.map(orderRequestDTO, UserTransaction.class));
                    return new PaymentEvent(paymentRequestDTO, PaymentStatus.PAYMENT_COMPLETED);
                }).orElse(new PaymentEvent(paymentRequestDTO, PaymentStatus.PAYMENT_FAILED));
    }
}
