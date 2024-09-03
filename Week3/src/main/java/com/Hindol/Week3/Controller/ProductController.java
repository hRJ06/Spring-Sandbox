package com.Hindol.Week3.Controller;

import com.Hindol.Week3.Entity.ProductEntity;
import com.Hindol.Week3.Repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    final private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<ProductEntity> getAllProducts(@RequestParam(required = false, defaultValue = "id") String sortBy) {
        /*
            return productRepository.findByTitleOrderByPrice("Amul Cheese");
            return productRepository.findByOrderByPrice();
            return productRepository.findBy(Sort.by(sortBy));
            return productRepository.findBy(Sort.by(Sort.Direction.DESC, sortBy, "price"));
            return productRepository.findBy(Sort.by(Sort.Direction.DESC, sortBy).and(Sort.by(Sort.Direction.ASC, "price")));
            return productRepository.findBy(Sort.by(Sort.Order.desc(sortBy)));
        */
        return productRepository.findBy(Sort.by(
                    Sort.Order.desc(sortBy),
                    (Sort.Order.asc("title")
                )));
    }
}
