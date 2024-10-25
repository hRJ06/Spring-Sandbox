package com.Hindol.Inventory.Repository;

import com.Hindol.Inventory.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
