package com.Hindol.PaymentService.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "USER_BALANCE_TBL")
public class UserBalance {
    @Id
    private Integer userId;
    private Integer balance;
}
