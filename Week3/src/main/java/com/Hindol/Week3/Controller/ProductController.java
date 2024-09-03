package com.Hindol.Week3.Controller;

import com.Hindol.Week3.Entity.ProductEntity;
import com.Hindol.Week3.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    private final int PAGE_SIZE = 2;
    final private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<ProductEntity> getAllProducts(@RequestParam(defaultValue = "") String title, @RequestParam(required = false, defaultValue = "id") String sortBy, @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
        /*
            return productRepository.findByTitleOrderByPrice("Amul Cheese");
            return productRepository.findByOrderByPrice();
            return productRepository.findBy(Sort.by(sortBy));
            return productRepository.findBy(Sort.by(Sort.Direction.DESC, sortBy, "price"));
            return productRepository.findBy(Sort.by(Sort.Direction.DESC, sortBy).and(Sort.by(Sort.Direction.ASC, "price")));
            return productRepository.findBy(Sort.by(Sort.Order.desc(sortBy)));
            return productRepository.findBy(Sort.by(
                    Sort.Order.desc(sortBy),
                    (Sort.Order.asc("title")
                )));
        */
        /* Pageable p = PageRequest.of(pageNumber, PAGE_SIZE); */
        Pageable p = PageRequest.of(pageNumber, PAGE_SIZE, Sort.by(Sort.Order.desc(sortBy)));
        /*
            return productRepository.findAll(p).getContent();
        */
        return productRepository.findByTitleContainingIgnoreCase(title,p);

    }
}
