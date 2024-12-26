package com.Hindol.Scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class StudentInfoService {
    @Async
    public CompletableFuture<String> getName() throws InterruptedException {
        log.info("Getting the Name - {}", Thread.currentThread().getName());
        Thread.sleep(2000);
        log.info("Returning Name");
        return CompletableFuture.completedFuture("Hindol Roy");
    }
    @Async
    public CompletableFuture<String> getId() throws InterruptedException {
        log.info("Getting the ID - {}", Thread.currentThread().getName());
        Thread.sleep(2000);
        log.info("Returning ID");
        return CompletableFuture.completedFuture("1");
    }
    @Async
    public CompletableFuture<String> getCollege() throws InterruptedException {
        log.info("Getting the College - {}", Thread.currentThread().getName());
        Thread.sleep(2000);
        log.info("Returning College");
        return CompletableFuture.completedFuture("KIIT");
    }
}
