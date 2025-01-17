package com.Hindol.PaymentService.Event;

import com.Hindol.PaymentService.DTO.PaymentRequestDTO;
import com.Hindol.PaymentService.Enum.PaymentStatus;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class PaymentEvent {
    private UUID eventId = UUID.randomUUID();
    private Date date = new Date();
    private PaymentRequestDTO paymentRequestDTO;
    private PaymentStatus paymentStatus;

    public PaymentEvent(PaymentRequestDTO paymentRequestDTO, PaymentStatus paymentStatus) {
        this.paymentRequestDTO = paymentRequestDTO;
        this.paymentStatus = paymentStatus;
    }
}