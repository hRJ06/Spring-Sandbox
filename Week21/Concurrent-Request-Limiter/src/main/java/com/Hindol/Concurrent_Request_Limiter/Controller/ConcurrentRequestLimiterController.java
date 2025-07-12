package com.Hindol.Concurrent_Request_Limiter.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ConcurrentRequestLimiterController {

    @GetMapping("/data")
    public ResponseEntity<String> getData() throws InterruptedException {
        Thread.sleep(5000);
        return ResponseEntity.ok("OK");
    }
}
