package com.Hindol.Inventory.Service;

import com.Hindol.Inventory.DTO.ProductDTO;
import com.Hindol.Inventory.Entity.Product;
import com.Hindol.Inventory.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDTO> getAllInventory() {
        log.info("Fetching all inventory items");
        List<Product> inventories = productRepository.findAll();
        return inventories.stream().map((element) -> modelMapper.map(element, ProductDTO.class)).toList();
    }

    public ProductDTO getProductById(Long id) {
        log.info("Fetching Product with ID : {}", id);
        Optional<Product> product = productRepository.findById(id);
        return product.map((element) -> modelMapper.map(element, ProductDTO.class)).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
