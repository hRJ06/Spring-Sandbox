package com.Hindol.ReviewService.Router;

import com.Hindol.ReviewService.Handler.ReviewHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.Hindol.ReviewService.Util.Constant.APPLICATION_HEALTH_ENDPOINT_PING;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ReviewRouter {
    @Bean
    public RouterFunction<ServerResponse> reviewRoute(ReviewHandler reviewHandler) {
        return route()
                .nest(path("/api/v1/review-service"), builder -> {
                    builder
                            .GET("/health/ping", request -> ServerResponse.ok().bodyValue(APPLICATION_HEALTH_ENDPOINT_PING))
                            .POST("", reviewHandler::addReview)
                            .GET("", reviewHandler::getReview)
                            .GET("/book/{bookId}", reviewHandler::getReviewByBookId)
                            .PUT("/{reviewId}", reviewHandler::updateReview)
                            .DELETE("/{reviewId}", reviewHandler::deleteReview)
                            .DELETE("/book/{bookId}", reviewHandler::deleteReviewByBookId);
                })
                .build();
    }
}
