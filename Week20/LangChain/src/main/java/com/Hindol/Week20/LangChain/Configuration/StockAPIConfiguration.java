package com.Hindol.Week20.LangChain.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import static com.Hindol.Week20.LangChain.Util.Constant.*;

@Configuration
public class StockAPIConfiguration {

    @Value("${stock.api.key}")
    private String APIKey;

    @Bean
    public RestClient getRestClient() {
        return RestClient.builder()
                .baseUrl(STOCK_API_BASE_URL)
                .build();
    }

    public String getAPIKey() {
        return APIKey;
    }
}
