package com.Hindol.Rate_Limiter.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoadShedderController {

    @GetMapping("/data")
    public String getData() {
        return "OK";
    }

    @GetMapping("/burn")
    public String burn() {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 5000) {
            Math.pow(Math.random(), Math.random());
        }
        return "CPU SPIKE DONE";
    }

}
