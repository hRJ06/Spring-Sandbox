package com.Hindol.BookService.Client;

import com.Hindol.BookService.Advice.APIError;
import com.Hindol.BookService.DTO.ReviewDTO;
import com.Hindol.BookService.Exception.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ReviewClient {
    @Autowired
    private WebClient webClient;

    @Value("${restClient.review-service-url}")
    private String reviewServiceURL;

    public Flux<ReviewDTO> getReview(Long bookId) {
        var url = reviewServiceURL.concat("/book/{bookId}");
        return webClient
                .get()
                .uri(url, bookId)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(APIError.class)
                                .flatMap(e -> Mono.error(new ClientException(e.getError(), e.getStatus())))
                )
                .bodyToFlux(ReviewDTO.class);
    }

    public Mono<Void> deleteReview(Long bookId) {
        var url = reviewServiceURL.concat("/book/{bookId}");
        return webClient
                .delete()
                .uri(url, bookId)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(APIError.class)
                                .flatMap(e -> Mono.error(new ClientException(e.getError(), e.getStatus())))
                )
                .bodyToMono(Void.class);
    }

}
