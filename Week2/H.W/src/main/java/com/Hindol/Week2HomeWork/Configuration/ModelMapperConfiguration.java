package com.Hindol.Week2HomeWork.Configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {
    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
