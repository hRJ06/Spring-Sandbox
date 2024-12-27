package com.Hindol.Scheduling.Service;

import com.Hindol.Scheduling.Entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final ReportService reportService;
    private final TaskScheduler taskScheduler;

    public User getUserReport() {
        long start = System.currentTimeMillis();
        log.info("Start get User Report - {}", Thread.currentThread().getName());
        try {
            CompletableFuture<String> userData = reportService.fetchUserData();
            CompletableFuture<String> orderData = reportService.fetchOrderData();
            CompletableFuture<Integer> paymentData = reportService.fetchPaymentData();

            CompletableFuture.allOf(userData, orderData, paymentData).join();
            User user = new User(userData.get(), orderData.get(), paymentData.get());
            long end = System.currentTimeMillis();
            log.info("Ended in {}", end - start);
            log.info("End get User Report - {}", Thread.currentThread().getName());
            return user;
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendEmail() {
        log.info("Start send email - {}", Thread.currentThread().getName());
        taskScheduler.schedule(() -> {
            log.info("Scheduler Start - {}", Thread.currentThread().getName());
            /* Mock Email sending */
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Scheduler End - {}", Thread.currentThread().getName());
        }, Instant.now().plusSeconds(10));
        log.info("End send email - {}", Thread.currentThread().getName());
    }
}
