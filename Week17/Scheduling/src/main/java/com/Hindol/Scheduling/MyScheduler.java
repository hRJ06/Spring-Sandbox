package com.Hindol.Scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyScheduler {
    /* Not concurrent */
    /* @Scheduled(fixedRate = 1000) */
    /* @Scheduled(fixedDelay = 200, initialDelay = 10000) */
    // @Scheduled(cron = "*/5 * * * * *")
    @Scheduled(fixedRate = 200)
    @Async("jobExecutor") /* Has no limit on thread pool by default */
    void logMe() {
        log.info("Scheduler 1 started - {}", Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Scheduler ended- {}", Thread.currentThread().getName());
    }

    /* Not concurrent
        @Scheduled(fixedRate = 200)
        void logYou() {
            log.info("Scheduler 2 started - {}", Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Scheduler ended- {}", Thread.currentThread().getName());
        }
     */
}
