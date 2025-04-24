package com.Hindol.ReviewService.Repository;

import com.Hindol.ReviewService.Entity.Review;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends ReactiveCrudRepository<Review, Long> {
}
