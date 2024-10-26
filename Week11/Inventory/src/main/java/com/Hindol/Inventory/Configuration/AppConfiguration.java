package com.Hindol.Inventory.Configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfiguration {
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
    @Bean
    public RestClient getRestClient() {
        return RestClient.builder().build();
    }
}
