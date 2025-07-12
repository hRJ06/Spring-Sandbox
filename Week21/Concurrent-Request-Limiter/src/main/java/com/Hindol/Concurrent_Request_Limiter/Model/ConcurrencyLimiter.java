package com.Hindol.Concurrent_Request_Limiter.Model;

import com.Hindol.Concurrent_Request_Limiter.Configuration.LimiterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;

@Component
public class ConcurrencyLimiter {

    private final Semaphore semaphore;
    private final LimiterConfiguration limiterConfiguration;

    @Autowired
    public ConcurrencyLimiter(LimiterConfiguration configuration) {
        this.limiterConfiguration = configuration;
        this.semaphore = new Semaphore(configuration.getMaxConcurrentRequest());
    }

    public boolean tryAcquire() {
        return semaphore.tryAcquire();
    }

    public void release() {
        semaphore.release();
    }

    public int getAvailablePermit() {
        return semaphore.availablePermits();
    }

}
