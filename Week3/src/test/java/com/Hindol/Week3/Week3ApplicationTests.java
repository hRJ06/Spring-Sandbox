package com.Hindol.Week3;

import com.Hindol.Week3.Entity.ProductEntity;
import com.Hindol.Week3.Repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class Week3ApplicationTests {
	@Autowired
	ProductRepository productRepository;
	@Test
	void contextLoads() {
	}
	@Test
	void testRepository() {
		ProductEntity productEntity = ProductEntity.builder().sku("nestle123").title("Nestle Chocolate").price(BigDecimal.valueOf(123.45)).quantity(12).build();
		ProductEntity savedProduct = productRepository.save(productEntity);
		System.out.println(savedProduct);
	}
	@Test
	void getRepository() {
		/*
			List<ProductEntity> entities = productRepository.findByTitle("Pepsi");
			List<ProductEntity> entities = productRepository.findByCreatedAtAfter(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
			List<ProductEntity> entities = productRepository.findByQuantityAndPrice(11, BigDecimal.valueOf(123.45));
			List<ProductEntity> entities = productRepository.findByQuantityGreaterThanAndPriceLessThan(3, BigDecimal.valueOf(15));
			List<ProductEntity> entities = productRepository.findByQuantityGreaterThanOrPriceLessThan(3, BigDecimal.valueOf(15));
			List<ProductEntity> entities = productRepository.findByTitleLike("%Nestle");
			List<ProductEntity> entities = productRepository.findByTitleLike("%Nestle%");
			List<ProductEntity> entities = productRepository.findByTitleLike("Nestle%");
			List<ProductEntity> entities = productRepository.findByTitleContaining("choco"); CASE INSENSITIVE IN MYSQL, BUT NOT IN POSTGRES
			List<ProductEntity> entities = productRepository.findByTitleContainingIgnoreCase("choco");
		*/
		List<ProductEntity> entities = productRepository.findByTitleContainingIgnoreCase("choco");
		System.out.println(entities);
	}
	@Test
	void getSingleFromRepository() {
		Optional<ProductEntity> entity = productRepository.findByTitleAndPrice("Pepsi", BigDecimal.valueOf(14.4));
		entity.ifPresent(System.out::println);
	}
}
