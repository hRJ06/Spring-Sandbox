package com.Hindol.Week5.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
public class AppConfiguration {
    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
