package com.Hindol.Week4HW.Client.Implementation;

import com.Hindol.Week4HW.Client.CurrencyClient;
import com.Hindol.Week4HW.DTO.CurrencyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CurrencyClientImplementation implements CurrencyClient {
    private final RestClient restClient;

    Logger log = LoggerFactory.getLogger(CurrencyClientImplementation.class);

    public CurrencyClientImplementation(RestClient restClient) {
        this.restClient = restClient;
    }
    //    https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_D4xSvKqmj4Ld99JbFYrQCEnuMscHrDU5mda4rVQo&currencies=USD&base_currency=INR
    @Override
    public Double convertCurrency(String from, String to, Double amount) {
        log.info("FROM : {}, TO : {}, AMOUNT: {}", from, to, amount);
        try {
            CurrencyDTO currencyDTO = restClient.get()
                    .uri("/v1/latest?currencies={to}&base_currency={from}", to, from)
                    .header("apikey","fca_live_D4xSvKqmj4Ld99JbFYrQCEnuMscHrDU5mda4rVQo")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new RuntimeException("Could not convert currency");
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            log.debug("API Response : {}", currencyDTO);
            return (Double) currencyDTO.getData().get(to) * amount;
        }
        catch (Exception e) {
            log.error("Exception occurred in convertCurrency", e);
            throw new RuntimeException(e);
        }
    }
}
