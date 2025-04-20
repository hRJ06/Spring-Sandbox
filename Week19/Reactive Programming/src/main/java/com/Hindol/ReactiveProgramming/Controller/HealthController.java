package com.Hindol.ReactiveProgramming.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static com.Hindol.ReactiveProgramming.Util.Constant.APPLICATION_HEALTH_ENDPOINT_PING;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping("/ping")
    public Mono<String> checkHealth() {
        return Mono.just(APPLICATION_HEALTH_ENDPOINT_PING);
    }

    @GetMapping(value = "/ping-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> checkHealthStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> APPLICATION_HEALTH_ENDPOINT_PING + seq);
    }

}
