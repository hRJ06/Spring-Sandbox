package com.Hindol.Scheduling.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ReportService {

    @Async
    public CompletableFuture<String> fetchUserData() throws InterruptedException {
        log.info("Start fetch User data - {}", Thread.currentThread().getName());
        Thread.sleep(2000);
        log.info("End fetch User data - {}", Thread.currentThread().getName());
        return CompletableFuture.completedFuture("Hindol Roy");
    }

    @Async
    public CompletableFuture<String> fetchOrderData() throws InterruptedException {
        log.info("Start fetch Order data - {}", Thread.currentThread().getName());
        Thread.sleep(3000);
        log.info("End fetch Order data - {}", Thread.currentThread().getName());
        return CompletableFuture.completedFuture("Microservice Design Pattern");
    }

    @Async
    public CompletableFuture<Integer> fetchPaymentData() throws InterruptedException {
        log.info("Start fetch Payment data - {}", Thread.currentThread().getName());
        Thread.sleep(4000);
        log.info("End fetch Payment data - {}", Thread.currentThread().getName());
        return CompletableFuture.completedFuture(1000);
    }
}
