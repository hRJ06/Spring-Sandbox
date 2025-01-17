package com.Hindol.PaymentService.DTO;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Integer orderId;
    private Integer userId;
    private Integer amount;
}
