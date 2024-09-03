package com.Hindol.Week3.Repository;

import com.Hindol.Week3.Entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByTitle(String title);
    List<ProductEntity> findByCreatedAtAfter(LocalDateTime localDateTime);
    List<ProductEntity> findByQuantityAndPrice(Integer quantity, BigDecimal price);
    List<ProductEntity> findByQuantityGreaterThanAndPriceLessThan(Integer quantity, BigDecimal price);
    List<ProductEntity> findByQuantityGreaterThanOrPriceLessThan(Integer quantity, BigDecimal price);
    List<ProductEntity> findByTitleLike(String title);
    List<ProductEntity> findByTitleContaining(String title);
    List<ProductEntity> findByTitleContainingIgnoreCase(String title);
    /* Optional<ProductEntity> findByTitleAndPrice(String title, BigDecimal price); */


    /* @Query("SELECT P FROM ProductEntity P WHERE P.title = ?1 AND P.price = ?2") */
    /* @Query("SELECT P FROM ProductEntity P WHERE P.title = :title AND P.price = :price") */
    @Query(value = "SELECT * FROM product_table WHERE product_title = ?1 AND price = ?2", nativeQuery = true)
    Optional<ProductEntity> findByTitleAndPrice(String title, BigDecimal price);

    /* SORTING */
    List<ProductEntity> findByTitleOrderByPrice(String title);
    List<ProductEntity> findByOrderByPrice();
    List<ProductEntity> findBy(Sort sort);

    /* PAGINATION */
    List<ProductEntity> findByTitle(String tile, Pageable pageable);
    List<ProductEntity> findByTitleContainingIgnoreCase(String title, Pageable p);

}
