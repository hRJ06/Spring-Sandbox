package com.Hindol.ReviewService.Handler;

import com.Hindol.ReviewService.DTO.ReviewDTO;
import com.Hindol.ReviewService.Exception.ResourceNotFoundException;
import com.Hindol.ReviewService.Mapper.ReviewMapper;
import com.Hindol.ReviewService.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ReviewHandler {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewMapper reviewMapper;

    public Mono<ServerResponse> addReview(ServerRequest request) {
        return request.bodyToMono(ReviewDTO.class)
                .map(reviewMapper::dtoToEntity)
                .flatMap(reviewRepository::save)
                .flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue);
    }

    public Mono<ServerResponse> getReview(ServerRequest serverRequest) {
        var reviewFlux = reviewRepository.findAll().map(reviewMapper::entityToDTO);
        return ServerResponse.ok().body(reviewFlux, ReviewDTO.class);
    }

    public Mono<ServerResponse> updateReview(ServerRequest serverRequest) {
        Long reviewId = Long.valueOf(serverRequest.pathVariable("reviewId"));
        var existingReview = reviewRepository.findById(reviewId);
        return existingReview.flatMap(review ->
            serverRequest.bodyToMono(ReviewDTO.class)
                    .map(reqReview -> {
                        review.setComment(reqReview.getComment());
                        review.setRating(reqReview.getRating());
                        return review;
                    })
                    .flatMap(reviewRepository::save)
                    .flatMap(ServerResponse.ok()::bodyValue)
        );
    }

    public Mono<ServerResponse> deleteReview(ServerRequest serverRequest) {
        Long reviewId = Long.valueOf(serverRequest.pathVariable("reviewId"));
        var existingReview = reviewRepository.findById(reviewId);
        return existingReview
                .flatMap(reviewRepository::delete)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getReviewByBookId(ServerRequest serverRequest) {
        Long bookId = Long.valueOf(serverRequest.pathVariable("bookId"));

        return reviewRepository.findByBookId(bookId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Review", "bookId", String.valueOf(bookId))))
                .map(reviewMapper::entityToDTO)
                .flatMap(ServerResponse.ok()::bodyValue);
    }
}
