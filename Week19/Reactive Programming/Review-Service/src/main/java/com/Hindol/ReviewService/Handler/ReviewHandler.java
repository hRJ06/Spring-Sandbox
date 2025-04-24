package com.Hindol.ReviewService.Handler;

import com.Hindol.ReviewService.DTO.ReviewDTO;
import com.Hindol.ReviewService.Entity.Review;
import com.Hindol.ReviewService.Mapper.ReviewMapper;
import com.Hindol.ReviewService.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
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
}
