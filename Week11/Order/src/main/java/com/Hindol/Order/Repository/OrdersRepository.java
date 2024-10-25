package com.Hindol.Order.Repository;

import com.Hindol.Order.Entity.Orders;
import lombok.extern.java.Log;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
