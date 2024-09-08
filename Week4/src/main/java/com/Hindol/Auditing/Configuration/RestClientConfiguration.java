package com.Hindol.Auditing.Configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Configuration
public class RestClientConfiguration {

    @Value("${departmentService.base.url}")
    private String BASE_URL;

    @Bean
    @Qualifier("DepartmentServiceClient")
    RestClient getRestClient() {
        return RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new RuntimeException("INTERNAL SERVER ERROR");
                })
                .build();
    }
}
