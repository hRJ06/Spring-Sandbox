package com.Hindol.ReviewService.Repository;

import com.Hindol.ReviewService.Entity.Review;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReviewRepository extends ReactiveCrudRepository<Review, Long> {
    Flux<Review> findByBookId(Long bookId);
    Mono<Void> deleteByBookId(Long bookId);
}
