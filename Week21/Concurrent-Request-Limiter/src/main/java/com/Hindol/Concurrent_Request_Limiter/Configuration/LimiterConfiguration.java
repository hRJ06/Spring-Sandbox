package com.Hindol.Concurrent_Request_Limiter.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "limiter")
public class LimiterConfiguration {
    private int maxConcurrentRequest;

    public int getMaxConcurrentRequest() {
        return maxConcurrentRequest;
    }

    public void setMaxConcurrentRequest(int maxConcurrentRequest) {
        this.maxConcurrentRequest = maxConcurrentRequest;
    }
}
