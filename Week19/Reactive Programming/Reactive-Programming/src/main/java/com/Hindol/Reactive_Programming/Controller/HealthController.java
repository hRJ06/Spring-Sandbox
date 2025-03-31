package com.Hindol.Reactive_Programming.Controller;

import com.Hindol.Reactive_Programming.Util.Constant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.Hindol.Reactive_Programming.Util.Constant.APPLICATION_HEALTH_ENDPOINT_PING;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping("/ping")
    public Mono<String> checkHealth() {
        return Mono.just(APPLICATION_HEALTH_ENDPOINT_PING);
    }

}
