package com.Hindol.PaymentService.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "USER_TRANSACTION_TBL")
public class UserTransaction {
    @Id
    private Integer orderId;
    private Integer userId;
    private Integer amount;
}
