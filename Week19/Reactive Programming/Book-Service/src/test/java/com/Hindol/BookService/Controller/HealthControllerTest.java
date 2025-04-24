package com.Hindol.BookService.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import static com.Hindol.BookService.Util.Constant.APPLICATION_HEALTH_ENDPOINT_PING;

@AutoConfigureWebTestClient
@WebFluxTest(controllers = HealthController.class)
class HealthControllerTest {

    @Autowired
    WebTestClient webTestClient;

    private static String HEALTH_ENDPOINT = "/api/v1/health/";
    @Test
    void checkHealth() {

        /*
            APPROACH - 1
            webTestClient
                    .get()
                    .uri("/api/v1/health/ping")
                    .exchange()
                    .expectStatus()
                    .is2xxSuccessful()
                    .expectBody(String.class)
                    .isEqualTo(APPLICATION_HEALTH_ENDPOINT_PING);
         */

        /*
            APPROACH - 2
            var mono = webTestClient
                    .get()
                    .uri("/api/v1/health/ping")
                    .exchange()
                    .expectStatus()
                    .is2xxSuccessful()
                    .returnResult(String.class)
                    .getResponseBody();
            StepVerifier.create(mono)
                    .expectNext(APPLICATION_HEALTH_ENDPOINT_PING)
                    .verifyComplete();
         */

        /* APPROACH - 3 */
        webTestClient
                .get()
                .uri(HEALTH_ENDPOINT + "/ping")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class) /* expectBodyList -> FLUX */
                .consumeWith(body -> {
                    var responseBody = body.getResponseBody();
                    assert(responseBody).equals(APPLICATION_HEALTH_ENDPOINT_PING);
                });
    }

    @Test
    void checkHealthStream() {
        var mono = webTestClient
                .get()
                .uri(HEALTH_ENDPOINT + "/ping-stream")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(String.class)
                .getResponseBody();
        StepVerifier.create(mono)
                .expectNext(APPLICATION_HEALTH_ENDPOINT_PING + "0", APPLICATION_HEALTH_ENDPOINT_PING + "1",APPLICATION_HEALTH_ENDPOINT_PING + "2")
                .thenCancel()
                .verify();
    }


}