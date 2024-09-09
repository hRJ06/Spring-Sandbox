package com.Hindol.Week4HW.Controller;

import com.Hindol.Week4HW.Client.CurrencyClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/convertCurrency")
public class CurrencyConverterController {
    private final CurrencyClient currencyClient;

    public CurrencyConverterController(CurrencyClient currencyClient) {
        this.currencyClient = currencyClient;
    }

    @GetMapping
    public ResponseEntity<Double> getRate(@RequestParam String fromCurrency, @RequestParam String toCurrency, @RequestParam Double units) {
        return ResponseEntity.ok(currencyClient.convertCurrency(fromCurrency, toCurrency, units));
    }
}
