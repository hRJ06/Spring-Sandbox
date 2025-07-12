package com.Hindol.Request_Rate_Limiter_Application.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DataController {
    @GetMapping("/data")
    public String getData() {
        return "OK";
    }
}
